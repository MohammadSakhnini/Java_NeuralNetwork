public enum WeightGenerator {
    /**
     *Randomly generated weights between [-1,1]
     */
    Random,
   
   /**
    * Prefered for Tanh ReLu & leaky ReLu Activation functions
    */
   He,
   /**
    * Prefered for Tanh and Sigmoid Activation functions
    */
   Xavier,
   Zeros
}
