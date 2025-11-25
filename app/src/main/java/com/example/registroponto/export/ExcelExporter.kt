package com.example.registroponto.export

import android.content.Context
import com.example.registroponto.data.Registro
import com.example.registroponto.data.TipoRegistro
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class ExcelExporter(private val context: Context) {

    data class JornadaDia(
        val data: String,
        val registros: List<Registro>,
        val totalHoras: Double
    )

    fun exportarParaXLSX(registros: List<Registro>): File? {
        return try {
            val workbook: Workbook = XSSFWorkbook()

            // Agrupar registros por dia
            val registrosPorDia = agruparRegistrosPorDia(registros)

            // Criar abas
            criarAbaDetalhada(workbook, registrosPorDia)
            criarAbaResumo(workbook, registrosPorDia)

            // Salvar arquivo
            val fileName = "RegistrosPonto_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.xlsx"
            val file = File(context.getExternalFilesDir(null), fileName)

            FileOutputStream(file).use { outputStream ->
                workbook.write(outputStream)
            }

            workbook.close()
            file

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun agruparRegistrosPorDia(registros: List<Registro>): List<JornadaDia> {
        val sdfDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return registros
            .groupBy { sdfDate.format(Date(it.dataHora)) }
            .map { (data, registrosDia) ->
                val horasTrabalhadas = calcularHorasTrabalhadas(registrosDia)
                JornadaDia(data, registrosDia.sortedBy { it.dataHora }, horasTrabalhadas)
            }
            .sortedBy { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(it.data) }
    }

    private fun calcularHorasTrabalhadas(registros: List<Registro>): Double {
        var totalMinutos = 0.0
        var ultimaEntrada: Long? = null

        registros.sortedBy { it.dataHora }.forEach { registro ->
            when (registro.tipo) {
                TipoRegistro.ENTRADA -> {
                    ultimaEntrada = registro.dataHora
                }
                TipoRegistro.SAIDA -> {
                    ultimaEntrada?.let { entrada ->
                        val diff = registro.dataHora - entrada
                        totalMinutos += diff / (1000.0 * 60.0)
                        ultimaEntrada = null
                    }
                }
            }
        }

        return totalMinutos / 60.0 // Retorna em horas
    }

    private fun criarAbaDetalhada(workbook: Workbook, jornadas: List<JornadaDia>) {
        val sheet = workbook.createSheet("Registros Detalhados")
        var rowNum = 0

        val headerStyle = createHeaderStyle(workbook)
        val titleStyle = createTitleStyle(workbook)
        val totalStyle = createTotalStyle(workbook)
        val dateStyle = createDateCellStyle(workbook)

        // Título
        val titleRow = sheet.createRow(rowNum++)
        val titleCell = titleRow.createCell(0)
        titleCell.setCellValue("RELATÓRIO DE PONTO - DETALHADO")
        titleCell.cellStyle = titleStyle
        sheet.addMergedRegion(CellRangeAddress(0, 0, 0, 6))
        rowNum++

        jornadas.forEach { jornada ->
            // Cabeçalho do dia
            val dayHeaderRow = sheet.createRow(rowNum++)
            val dayCell = dayHeaderRow.createCell(0)
            dayCell.setCellValue("Data: ${jornada.data}")
            dayCell.cellStyle = createDayHeaderStyle(workbook)
            sheet.addMergedRegion(CellRangeAddress(rowNum - 1, rowNum - 1, 0, 6))

            // Cabeçalho das colunas
            val headerRow = sheet.createRow(rowNum++)
            val headers = arrayOf("Tipo", "Hora", "Latitude", "Longitude", "Observação")
            headers.forEachIndexed { index, header ->
                val cell = headerRow.createCell(index)
                cell.setCellValue(header)
                cell.cellStyle = headerStyle
            }

            // Registros do dia
            jornada.registros.forEach { registro ->
                val row = sheet.createRow(rowNum++)
                val sdfTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

                row.createCell(0).setCellValue(registro.tipo.name)
                row.createCell(1).setCellValue(sdfTime.format(Date(registro.dataHora)))
                row.createCell(2).setCellValue(String.format("%.6f", registro.latitude))
                row.createCell(3).setCellValue(String.format("%.6f", registro.longitude))
                row.createCell(4).setCellValue(registro.getLocalizacaoFormatada())
            }

            // Total do dia
            val totalRow = sheet.createRow(rowNum++)
            val totalCell = totalRow.createCell(0)
            totalCell.setCellValue("Total do Dia:")
            totalCell.cellStyle = totalStyle

            val horasCell = totalRow.createCell(1)
            horasCell.setCellValue(formatarHoras(jornada.totalHoras))
            horasCell.cellStyle = totalStyle

            rowNum++ // Linha em branco entre dias
        }

        // Total geral
        val totalGeral = jornadas.sumOf { it.totalHoras }
        rowNum++
        val totalGeralRow = sheet.createRow(rowNum++)
        val totalGeralCell = totalGeralRow.createCell(0)
        totalGeralCell.setCellValue("TOTAL GERAL:")
        totalGeralCell.cellStyle = createTotalGeralStyle(workbook)

        val horasGeralCell = totalGeralRow.createCell(1)
        horasGeralCell.setCellValue(formatarHoras(totalGeral))
        horasGeralCell.cellStyle = createTotalGeralStyle(workbook)

        // Ajustar larguras
        for (i in 0..6) {
            sheet.autoSizeColumn(i)
        }
    }

    private fun criarAbaResumo(workbook: Workbook, jornadas: List<JornadaDia>) {
        val sheet = workbook.createSheet("Resumo Mensal")
        var rowNum = 0

        val headerStyle = createHeaderStyle(workbook)
        val titleStyle = createTitleStyle(workbook)
        val totalStyle = createTotalStyle(workbook)

        // Título
        val titleRow = sheet.createRow(rowNum++)
        val titleCell = titleRow.createCell(0)
        titleCell.setCellValue("RESUMO MENSAL DE HORAS")
        titleCell.cellStyle = titleStyle
        sheet.addMergedRegion(CellRangeAddress(0, 0, 0, 3))
        rowNum++

        // Cabeçalho
        val headerRow = sheet.createRow(rowNum++)
        val headers = arrayOf("Data", "Horas Trabalhadas", "Entradas", "Saídas")
        headers.forEachIndexed { index, header ->
            val cell = headerRow.createCell(index)
            cell.setCellValue(header)
            cell.cellStyle = headerStyle
        }

        // Agrupar por mês
        val jornadasPorMes = jornadas.groupBy {
            val mes = it.data.substring(3) // MM/yyyy
            mes
        }

        jornadasPorMes.forEach { (mes, jornadasMes) ->
            // Cabeçalho do mês
            val mesHeaderRow = sheet.createRow(rowNum++)
            val mesCell = mesHeaderRow.createCell(0)
            mesCell.setCellValue("Mês: $mes")
            mesCell.cellStyle = createDayHeaderStyle(workbook)
            sheet.addMergedRegion(CellRangeAddress(rowNum - 1, rowNum - 1, 0, 3))

            // Dias do mês
            jornadasMes.forEach { jornada ->
                val row = sheet.createRow(rowNum++)
                row.createCell(0).setCellValue(jornada.data)
                row.createCell(1).setCellValue(formatarHoras(jornada.totalHoras))

                val entradas = jornada.registros.count { it.tipo == TipoRegistro.ENTRADA }
                val saidas = jornada.registros.count { it.tipo == TipoRegistro.SAIDA }
                row.createCell(2).setCellValue(entradas.toDouble())
                row.createCell(3).setCellValue(saidas.toDouble())
            }

            // Total do mês
            val totalMes = jornadasMes.sumOf { it.totalHoras }
            val totalMesRow = sheet.createRow(rowNum++)
            val totalMesCell = totalMesRow.createCell(0)
            totalMesCell.setCellValue("Total do Mês:")
            totalMesCell.cellStyle = totalStyle

            val horasMesCell = totalMesRow.createCell(1)
            horasMesCell.setCellValue(formatarHoras(totalMes))
            horasMesCell.cellStyle = totalStyle

            rowNum++ // Linha em branco
        }

        // Total geral
        val totalGeral = jornadas.sumOf { it.totalHoras }
        rowNum++
        val totalGeralRow = sheet.createRow(rowNum++)
        val totalGeralCell = totalGeralRow.createCell(0)
        totalGeralCell.setCellValue("TOTAL GERAL:")
        totalGeralCell.cellStyle = createTotalGeralStyle(workbook)

        val horasGeralCell = totalGeralRow.createCell(1)
        horasGeralCell.setCellValue(formatarHoras(totalGeral))
        horasGeralCell.cellStyle = createTotalGeralStyle(workbook)

        // Ajustar larguras
        for (i in 0..3) {
            sheet.autoSizeColumn(i)
        }
    }

    private fun formatarHoras(horas: Double): String {
        val h = horas.toInt()
        val m = ((horas - h) * 60).toInt()
        return String.format("%dh %02dmin", h, m)
    }

    private fun createHeaderStyle(workbook: Workbook): CellStyle {
        val style = workbook.createCellStyle()
        val font = workbook.createFont()
        font.bold = true
        font.fontHeightInPoints = 11
        style.setFont(font)
        style.fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
        style.fillPattern = FillPatternType.SOLID_FOREGROUND
        style.borderBottom = BorderStyle.THIN
        style.borderTop = BorderStyle.THIN
        style.borderLeft = BorderStyle.THIN
        style.borderRight = BorderStyle.THIN
        style.alignment = HorizontalAlignment.CENTER
        return style
    }

    private fun createTitleStyle(workbook: Workbook): CellStyle {
        val style = workbook.createCellStyle()
        val font = workbook.createFont()
        font.bold = true
        font.fontHeightInPoints = 14
        style.setFont(font)
        style.alignment = HorizontalAlignment.CENTER
        style.fillForegroundColor = IndexedColors.LIGHT_BLUE.index
        style.fillPattern = FillPatternType.SOLID_FOREGROUND
        return style
    }

    private fun createDayHeaderStyle(workbook: Workbook): CellStyle {
        val style = workbook.createCellStyle()
        val font = workbook.createFont()
        font.bold = true
        font.fontHeightInPoints = 12
        style.setFont(font)
        style.fillForegroundColor = IndexedColors.LIGHT_CORNFLOWER_BLUE.index
        style.fillPattern = FillPatternType.SOLID_FOREGROUND
        return style
    }

    private fun createTotalStyle(workbook: Workbook): CellStyle {
        val style = workbook.createCellStyle()
        val font = workbook.createFont()
        font.bold = true
        style.setFont(font)
        style.fillForegroundColor = IndexedColors.LIGHT_YELLOW.index
        style.fillPattern = FillPatternType.SOLID_FOREGROUND
        return style
    }

    private fun createTotalGeralStyle(workbook: Workbook): CellStyle {
        val style = workbook.createCellStyle()
        val font = workbook.createFont()
        font.bold = true
        font.fontHeightInPoints = 12
        style.setFont(font)
        style.fillForegroundColor = IndexedColors.LIGHT_GREEN.index
        style.fillPattern = FillPatternType.SOLID_FOREGROUND
        return style
    }

    private fun createDateCellStyle(workbook: Workbook): CellStyle {
        val style = workbook.createCellStyle()
        style.dataFormat = workbook.createDataFormat().getFormat("dd/MM/yyyy HH:mm:ss")
        return style
    }
}
