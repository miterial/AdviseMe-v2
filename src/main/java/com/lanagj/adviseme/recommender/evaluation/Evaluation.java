package com.lanagj.adviseme.recommender.evaluation;

import com.lanagj.adviseme.entity.similarity.CompareResult;
import com.lanagj.adviseme.recommender.nlp.lsa.LatentSemanticAnalysis;
import com.lanagj.adviseme.recommender.nlp.lsa.ModifiedLatentSemanticAnalysis;
import com.lanagj.adviseme.recommender.nlp.lsa.OriginalLatentSemanticAnalysis;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class that determines how good the recommender system is
 */
@Service
public class Evaluation {

    OriginalLatentSemanticAnalysis latentSemanticAnalysis;
    ModifiedLatentSemanticAnalysis modifiedLatentSemanticAnalysis;

    public Evaluation(OriginalLatentSemanticAnalysis latentSemanticAnalysis, ModifiedLatentSemanticAnalysis modifiedLatentSemanticAnalysis) {

        this.latentSemanticAnalysis = latentSemanticAnalysis;
        this.modifiedLatentSemanticAnalysis = modifiedLatentSemanticAnalysis;
    }

    public void mlsaDifference() {

        Set<CompareResult> lsa = this.latentSemanticAnalysis.run();
        Set<CompareResult> mlsa = this.modifiedLatentSemanticAnalysis.run();

        Map<CompareResult.CompareId, CompareResult> lsaMap = lsa.stream().collect(Collectors.toMap(CompareResult::getId_pair, Function.identity()));

        Map<CompareResult.CompareId, CompareResult> mlsaMap = mlsa.stream().collect(Collectors.toMap(CompareResult::getId_pair, Function.identity()));

        try {
            this.exportToExcel(lsaMap, mlsaMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportToExcel(Map<CompareResult.CompareId, CompareResult> lsaMap,
                               Map<CompareResult.CompareId, CompareResult> mlsaMap) throws IOException {

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "test";

        for(File file : new java.io.File(fileLocation).listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("evaluation");

        int rowNum = 0;

        Row header = sheet.createRow(rowNum++);
        Cell valueCell = header.createCell(0);
        valueCell.setCellValue("IDs");

        Row lsaRow = sheet.createRow(rowNum++);
        valueCell = lsaRow.createCell(0);
        valueCell.setCellValue("LSA");
        Row lsaScaledRow = sheet.createRow(rowNum++);
        valueCell = lsaScaledRow.createCell(0);
        valueCell.setCellValue("LSA-s");

        Row mlsaRow = sheet.createRow(rowNum++);
        valueCell = mlsaRow.createCell(0);
        valueCell.setCellValue("MLSA");
        Row mlsaScaledRow = sheet.createRow(rowNum++);
        valueCell = mlsaScaledRow.createCell(0);
        valueCell.setCellValue("MLSA-s");

        fillTable(lsaMap, header, lsaRow, lsaScaledRow);
        fillTable(mlsaMap, header, mlsaRow, mlsaScaledRow);

        fileLocation += "/test.xlsx";
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }

    private void fillTable(Map<CompareResult.CompareId, CompareResult> resultMap, Row idRow, Row algRow, Row scaledRowCell) {

        int colNum = 0;

        Cell valueCell;
        for (Map.Entry<CompareResult.CompareId, CompareResult> resultMapEntry : resultMap.entrySet()) {

            valueCell = idRow.createCell(++colNum);
            valueCell.setCellValue(resultMapEntry.getKey().toString());

            valueCell = algRow.createCell(colNum);
            Double resLsa = resultMapEntry.getValue().getResult_lsa();
            valueCell.setCellValue(resLsa);

            int val;
            if(resLsa < 0.33) {
                val = 1;
            } else if(resLsa < 0.66) {
                val = 2;
            } else {
                val = 3;
            }
            valueCell = scaledRowCell.createCell(colNum);
            valueCell.setCellValue(val);
        }
    }

    public void precisionRecall() {

    }

    /**
     * <a href="https://link.springer.com/article/10.1007/s13042-017-0762-9#Equ26">Description and formula</a>
     */
    public void coverage() {

    }

}
