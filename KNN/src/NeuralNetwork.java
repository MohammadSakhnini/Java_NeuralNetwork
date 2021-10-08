import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NeuralNetwork {
    private InputLayer input;
    private List<HiddenLayer> hidden;
    private OutputLayer output;
    private double l_rate;

    public NeuralNetwork(InputLayer input, Collection<HiddenLayer> hidden, OutputLayer output, double l_rate) {
	this.input = input;
	this.hidden = new ArrayList<>(hidden);
	this.output = output;
	this.l_rate = l_rate;
    }

    public void train(double[][] x, double[][] y) throws Exception {
	
    }

}
