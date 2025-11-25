package com.example.registroponto.export

import android.content.Context
import com.example.registroponto.data.Registro
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ExcelExporter(private val context: Context) {

    fun exportarParaXLSX(registros: List<Registro>): File? {
        try {
            val workbook: Workbook = XSSFWorkbook()
            val sheet: Sheet = workbook.createSheet("Registros de Ponto")

            // Criar estilos
            val headerStyle = createHeaderStyle(workbook)
            val dateStyle = createDateStyle(workbook)

            // Criar cabeçalho
            val headerRow = sheet.createRow(0)
            val headers = arrayOf("Tipo", "Data", "Hora", "Latitude", "Longitude", "Localização")

            headers.forEachIndexed { index, header ->
                val cell = headerRow.createCell(index)
                cell.setCellValue(header)
                cell.cellStyle = headerStyle
            }

            // Adicionar dados
            registros.forEachIndexed { index, registro ->
                val row = sheet.createRow(index + 1)

                // Tipo
                row.createCell(0).setCellValue(registro.tipo.name)

                // Data
                val sdfDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                row.createCell(1).setCellValue(sdfDate.format(Date(registro.dataHora)))

                // Hora
                val sdfTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                row.createCell(2).setCellValue(sdfTime.format(Date(registro.dataHora)))

                // Latitude
                row.createCell(3).setCellValue(registro.latitude)

                // Longitude
                row.createCell(4).setCellValue(registro.longitude)

                // Localização formatada
                row.createCell(5).setCellValue(registro.getLocalizacaoFormatada())
            }

            // Ajustar largura das colunas
            for (i in 0 until headers.size) {
                sheet.autoSizeColumn(i)
            }

            // Salvar arquivo
            val fileName = "RegistrosPonto_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.xlsx"
            val file = File(context.getExternalFilesDir(null), fileName)

            FileOutputStream(file).use { outputStream ->
                workbook.write(outputStream)
            }

            workbook.close()

            return file

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun createHeaderStyle(workbook: Workbook): CellStyle {
        val style = workbook.createCellStyle()
        val font = workbook.createFont()
        font.bold = true
        font.fontHeightInPoints = 12
        style.setFont(font)
        style.fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
        style.fillPattern = FillPatternType.SOLID_FOREGROUND
        style.borderBottom = BorderStyle.THIN
        style.borderTop = BorderStyle.THIN
        style.borderLeft = BorderStyle.THIN
        style.borderRight = BorderStyle.THIN
        return style
    }

    private fun createDateStyle(workbook: Workbook): CellStyle {
        val style = workbook.createCellStyle()
        style.dataFormat = workbook.createDataFormat().getFormat("dd/MM/yyyy HH:mm:ss")
        return style
    }
}
