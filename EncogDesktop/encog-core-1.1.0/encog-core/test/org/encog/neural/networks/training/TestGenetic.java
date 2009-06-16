package org.encog.neural.networks.training;


import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.XOR;
import org.encog.neural.networks.training.genetic.TrainingSetNeuralGeneticAlgorithm;

import junit.framework.TestCase;

public class TestGenetic extends TestCase {
	public void testGenetic() throws Throwable
	{
		NeuralDataSet trainingData = new BasicNeuralDataSet(XOR.XOR_INPUT,XOR.XOR_IDEAL);
		BasicNetwork network = XOR.createThreeLayerNet();
				
		TrainingSetNeuralGeneticAlgorithm train = new TrainingSetNeuralGeneticAlgorithm(network, true, trainingData,500,0.1,0.25);	

		train.iteration();
		double error1 = train.getError();
		train.iteration();
		network = (BasicNetwork)train.getNetwork();
		double error2 = train.getError();
		
		double improve = (error1-error2)/error1;
		
		System.out.println(improve);
		
		TestCase.assertTrue("Genetic algorithm did not improve.",improve>0.0001);

	}
}
