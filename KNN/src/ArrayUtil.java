public class ArrayUtil {

    public static double[][] add(double[][] a, double[][] b) throws Exception {
	double results[][] = new double[a.length][a[0].length];

	if (a.length != b.length || a[0].length != b[0].length) {
	    throw new Exception("Invalid addition. Shape mismatch");
	}

	for (int i = 0; i < a.length; i++) {
	    for (int j = 0; j < a[0].length; j++) {
		results[i][j] = a[i][j] + b[i][j];
	    }
	}

	return results;
    }

    public static double[][] add(double[][] a, double scaler) {
	double results[][] = new double[a.length][a[0].length];

	for (int i = 0; i < a.length; i++) {
	    for (int j = 0; j < a[0].length; j++) {
		results[i][j] += scaler;
	    }

	}
	return results;
    }

    public static void add(double[][] a, double bias[]) {

	for (int i = 0; i < a.length; i++) {
	    for (int j = 0; j < a[0].length; j++) {
		a[i][j] += bias[i];
	    }

	}
    }

    public static double[][] subtract(double[][] a, double[][] b) throws Exception {
	double results[][] = new double[a.length][a[0].length];

	if (a.length != b.length || a[0].length != b[0].length) {
	    throw new Exception("Invalid addition. Shape mismatch");
	}
	for (int i = 0; i < a.length; i++) {
	    for (int j = 0; j < a[0].length; j++) {
		results[i][j] = a[i][j] - b[i][j];
	    }
	}
	return results;
    }

    public static double[][] transpose(double[][] a) {
	double results[][] = new double[a[0].length][a.length];

	for (int i = 0; i < a.length; i++) {
	    for (int j = 0; j < a[0].length; j++) {
		results[j][i] = a[i][j];
	    }
	}
	return results;
    }

    public static double[][] multiply(double[][] a, double[][] b) {
	double[][] results = new double[a.length][b[0].length];
	if (a[0].length != b.length) {
	    throw new IllegalArgumentException("A:Rows: " + a[0].length + " did not match B:Columns " + b.length + ".");
	}

	for (int i = 0; i < a.length; i++) { // aRow
	    for (int j = 0; j < b[0].length; j++) { // bColumn
		for (int k = 0; k < a[0].length; k++) { // aColumn
		    results[i][j] += a[i][k] * b[k][j];
		}
	    }
	}

	return results;
    }

    public static double[][] multiply(double[][] a, double scaler) {
	double results[][] = new double[a.length][a[0].length];

	for (int i = 0; i < a.length; i++) {
	    for (int j = 0; j < a[0].length; j++) {
		results[i][j] = a[i][j] * scaler;
	    }

	}
	return results;
    }

    public static void print(double[][] array) {
	if (array == null) {
	    return;
	}
	for (double[] x : array) {
	    for (double y : x) {
		System.out.print(y + " ");
	    }
	    System.out.println();
	}
    }

    public static double[] toArray(double[][] input) {
	double[] out = new double[input.length * input[0].length];
	for (int i = 0; i < input.length; i++) {
	    for (int j = 0; j < input[i].length; j++) {
		out[i + (j * input.length)] = input[i][j];
	    }
	}
	return out;
    }

    public static double[][] neutralArray(int rows, int columns) {
	double[][] results = new double[rows][columns];
	for (int i = 0; i < rows; i++) {
	    for (int j = 0; j < columns; j++) {
		results[i][j] = 1;
	    }
	}
	return results;
    }

}
