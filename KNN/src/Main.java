public class Main {
    public static void main(String[] args) throws Exception {

	double x[][] = { { 1, 2 }, { 4, 5 }, { 7, 8 }, { 10, 11 } };
	var layer = new InputLayer(x, WeightGenerator.Random, ActivationFunction.Relu);
	var layer1 = new HiddenLayer(2, 2, WeightGenerator.Random, ActivationFunction.Relu);
	
	
	layer.forward();
	ArrayUtil.print(layer.getOutput());
	System.out.println();
	layer.activationFunction();
	
	var output = layer.getOutput();
	
	layer1.forward(output);
	ArrayUtil.print(layer1.getOutput());
	System.out.println();
	layer1.activationFunction();

	
	ArrayUtil.print(layer1.getOutput());
	
	
	
    }
}
