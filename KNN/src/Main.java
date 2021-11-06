public class Main {
    public static void main(String[] args) throws Exception {
	// double target[] = { 0, 1, 1, 1 };// Exepcted output
	double inputs[][] = { { 1, 0, 0 } };// eingabe
	var inputLayer = new InputLayer(inputs, 3, WeightGenerator.Random, ActivationFunction.Identity);
	var hiddenLayer = new HiddenLayer(3, 4, WeightGenerator.Random, ActivationFunction.Sigmoid);
	NeuralNetwork nn = new NeuralNetwork(0.00005);
	nn.addLayer(inputLayer);
	nn.addLayer(hiddenLayer);
	var outputLayer = nn.addOutputLayer(4, ActivationFunction.Identity);
	double[][] input_weights = { { -0.081, 0.08, -0.04 }, { 0.06, 0.02, -0.003 }, { -0.01, 0.003, -0.09 } };
	double[][] input_bias = { { 0.08, -0.09, -0.05 } };
	double[][] hidden_weights = { { -0.008, 0.01, 0.01, 2.9E-4 }, { 0.06, -0.06, -0.027, -0.01 },
		{ 0.04, 0.06, 0.08, 0.08 } };
	double[][] hidden_bias = { { -0.08, 0.06, 0.09, -0.001 } };

	inputLayer.setWeights(input_weights);
	inputLayer.setBias(input_bias);
	hiddenLayer.setWeights(hidden_weights);
	hiddenLayer.setBias(hidden_bias);
	String path = "";
	String file = "";
	String file1 = "";
	String data = FileUtil.read(path + file);
	ArrayUtil.print(ArrayUtil.valuesToArray(data, ";"));
	FileUtil.saveTo(path + "test.txt", nn.toString());
    }

    private static void test_fromscratch() throws Exception {
	double inputs[][] = { { 1, 0, 0 } };// eingabe
	var inputLayer = new InputLayer(inputs, 3, WeightGenerator.Random, ActivationFunction.Identity);
	var hiddenLayer = new HiddenLayer(3, 4, WeightGenerator.Random, ActivationFunction.Sigmoid);
	NeuralNetwork nn = new NeuralNetwork(0.00005);
	nn.addLayer(inputLayer);
	nn.addLayer(hiddenLayer);
	var outputLayer = nn.addOutputLayer(4, ActivationFunction.Relu);

	double[][] input_weights = { { -0.081, 0.08, -0.04 }, { 0.06, 0.02, -0.003 }, { -0.01, 0.003, -0.09 } };
	double[][] input_bias = { { 0.08, -0.09, -0.05 } };
	double[][] hidden_weights = { { -0.008, 0.01, 0.01, 2.9E-4 }, { 0.06, -0.06, -0.027, -0.01 },
		{ 0.04, 0.06, 0.08, 0.08 } };
	double[][] hidden_bias = { { -0.08, 0.06, 0.09, -0.001 } };

	inputLayer.setWeights(input_weights);
	inputLayer.setBias(input_bias);
	inputLayer.forward();
	ArrayUtil.print(inputLayer.getOutput());

	hiddenLayer.setWeights(hidden_weights);
	hiddenLayer.setBias(hidden_bias);
	hiddenLayer.setNeurons(inputLayer.getOutput());
	hiddenLayer.activationFunction();
	ArrayUtil.print(hiddenLayer.getNeuronsValues());

	hiddenLayer.forward();
	ArrayUtil.print(hiddenLayer.getOutput());

	outputLayer.setNeurons(hiddenLayer.getOutput());
	var s = outputLayer.getNeurons();
	s[0][0].setFunction(ActivationFunction.Identity);
	outputLayer.activationFunction();
	ArrayUtil.print(outputLayer.getNeuronsValues());
    }

    public static void test_nn() throws Exception {
	double inputs[][] = { { 1, 0, 0 } };// eingabe
	var inputLayer = new InputLayer(inputs, 3, WeightGenerator.Random, ActivationFunction.Identity);
	var hiddenLayer = new HiddenLayer(3, 4, WeightGenerator.Random, ActivationFunction.Sigmoid);
	NeuralNetwork nn = new NeuralNetwork(0.05);
	nn.addLayer(inputLayer);
	nn.addLayer(hiddenLayer);
	var outputLayer = nn.addOutputLayer(4, ActivationFunction.Identity);

	double[][] input_weights = { { -0.081, 0.08, -0.04 }, { 0.06, 0.02, -0.003 }, { -0.01, 0.003, -0.09 } };
	double[][] input_bias = { { 0.08, -0.09, -0.05 } };
	double[][] hidden_weights = { { -0.008, 0.01, 0.01, 2.9E-4 }, { 0.06, -0.06, -0.027, -0.01 },
		{ 0.04, 0.06, 0.08, 0.08 } };
	double[][] hidden_bias = { { -0.08, 0.06, 0.09, -0.001 } };

	inputLayer.setWeights(input_weights);
	inputLayer.setBias(input_bias);
	hiddenLayer.setWeights(hidden_weights);
	hiddenLayer.setBias(hidden_bias);

	nn.feedForward();
	System.out.println(nn);
    }
}
