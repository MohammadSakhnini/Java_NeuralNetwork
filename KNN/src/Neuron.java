public class Neuron {
    private double value;
    private double bias;
    private ActivationFunction activation;

    public Neuron(double value) {
	this.value = value;
    }

    public Neuron(double value, ActivationFunction activation) {
	this.value = value;
	this.activation = activation;
    }

    public Neuron(double value, double bias) {
	this.value = value;
	this.bias = bias;
    }

    public Neuron(double value, double bias, ActivationFunction activation) {
	this(value, activation);
	this.bias = bias;

    }

    public void setBias(double bias) {
	this.bias = bias;
    }

    public void setFunction(ActivationFunction activation) {
	this.activation = activation;
    }

    public void setValue(double value) {
	this.value = value;
    }

    public double getValue() {
	return value;
    }

    public ActivationFunction getActivation() {
	return activation;
    }

}
