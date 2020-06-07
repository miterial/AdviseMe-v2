/*
package com.lanagj.adviseme.recommender.evaluation;

import com.lanagj.adviseme.configuration.AlgorithmType;
import com.lanagj.adviseme.entity.similarity.CompareResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

*/
/**
 * Class that determines how good the recommender system is
 *//*

@Service
@Deprecated
public class ManualEvaluation {

    OriginalLatentSemanticAnalysis latentSemanticAnalysis;
    ModifiedLatentSemanticAnalysis modifiedLatentSemanticAnalysis;

    public ManualEvaluation(OriginalLatentSemanticAnalysis latentSemanticAnalysis, ModifiedLatentSemanticAnalysis modifiedLatentSemanticAnalysis) {

        this.latentSemanticAnalysis = latentSemanticAnalysis;
        this.modifiedLatentSemanticAnalysis = modifiedLatentSemanticAnalysis;
    }

    public void mlsaDifference() {

        long startTime = new Date().getTime();

        CompletableFuture<Set<CompareResult>> lsa = this.latentSemanticAnalysis.run();
        CompletableFuture<Set<CompareResult>> mlsa = this.modifiedLatentSemanticAnalysis.run();

        Set<CompareResult> lsaJoin = lsa.join();
        Map<CompareResult.CompareId, CompareResult> lsaMap = lsaJoin.stream().collect(Collectors.toMap(CompareResult::getIdPair, Function.identity()));

        Set<CompareResult> mlsaJoin = mlsa.join();
        Map<CompareResult.CompareId, CompareResult> mlsaMap = mlsaJoin.stream().collect(Collectors.toMap(CompareResult::getIdPair, Function.identity()));

        System.out.println("Evaluation started -- " + (new Date().getTime() - startTime));

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

        for (File file : new java.io.File(fileLocation).listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("evaluation");

        int rowNum = 0;
        int colNum = 0;

        Row header = sheet.createRow(rowNum++);
        Cell valueCell = header.createCell(colNum++);
        valueCell.setCellValue("IDs");

        valueCell = header.createCell(colNum++);
        valueCell.setCellValue("LSA");
        valueCell = header.createCell(colNum++);
        valueCell.setCellValue("LSA-s");

        valueCell = header.createCell(colNum++);
        valueCell.setCellValue("MLSA");
        valueCell = header.createCell(colNum);
        valueCell.setCellValue("MLSA-s");

        List<Integer> movieIds = Arrays.asList(
                176403, 74643, 8329, 276624
                , 212162
                , 255962
                , 8386
                , 394568
                , 400642
                , 11547
        );

        for (CompareResult.CompareId compareId : lsaMap.keySet()) {
            if(compareId.containsAny(movieIds)) {
                Row row = sheet.createRow(rowNum++);
                fillTable(row, compareId, lsaMap.get(compareId), mlsaMap.get(compareId));
            }
        }

        fileLocation += "/test.xlsx";
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }

    private void fillTable(Row row, CompareResult.CompareId ids, CompareResult lsaMap, CompareResult mlsaMap) {

        int colNum = 0;

        Cell valueCell = row.createCell(colNum++);
        valueCell.setCellValue(ids.toString());

        // LSA
        valueCell = row.createCell(colNum++);
        Double resLsa = lsaMap.getResults().get(AlgorithmType.LSA);
        valueCell.setCellValue(resLsa);

        // Scaled LSA
        int val;
        if (resLsa < 0.33) {
            val = 1;
        } else if (resLsa < 0.66) {
            val = 2;
        } else {
            val = 3;
        }
        valueCell = row.createCell(colNum++);
        valueCell.setCellValue(val);

        // MLSA
        valueCell = row.createCell(colNum++);
        resLsa = mlsaMap.getResults().get(AlgorithmType.LSA);   //todo: change to MLSA
        valueCell.setCellValue(resLsa);

        // Scaled MLSA
        if (resLsa < 0.33) {
            val = 1;
        } else if (resLsa < 0.66) {
            val = 2;
        } else {
            val = 3;
        }
        valueCell = row.createCell(colNum++);
        valueCell.setCellValue(val);

    }

    public void precisionRecall() {

    }

    */
/**
     * <a href="https://link.springer.com/article/10.1007/s13042-017-0762-9#Equ26">Description and formula</a>
     *//*

    public void coverage() {

    }

}
*/
