package com.lanagj.adviseme.recommender.nlp.lsa.svd;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.springframework.stereotype.Service;

@Service
public class SVD {

    public double[][] solve(double[][] matrix, int k) {

        SingularValueDecomposition svd = new SingularValueDecomposition(MatrixUtils.createRealMatrix(matrix));

        double[][] truncatedU = new double[svd.getU().getRowDimension()][k];
        svd.getU().copySubMatrix(0, truncatedU.length - 1, 0, k - 1, truncatedU);

        double[][] truncatedS = new double[k][k];
        svd.getS().copySubMatrix(0, k - 1, 0, k - 1, truncatedS);

        double[][] truncatedVT = new double[k][svd.getVT().getColumnDimension()];
        svd.getVT().copySubMatrix(0, k - 1, 0, truncatedVT[0].length - 1, truncatedVT);

        RealMatrix approximatedSvdMatrix = (MatrixUtils.createRealMatrix(truncatedU)).multiply(
                MatrixUtils.createRealMatrix(truncatedS)).multiply(MatrixUtils.createRealMatrix(truncatedVT));

        return approximatedSvdMatrix.getData();
    }
}
