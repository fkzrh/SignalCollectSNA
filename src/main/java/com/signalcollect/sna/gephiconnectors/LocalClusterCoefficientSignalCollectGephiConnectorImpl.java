/*
 *  @author Flavio Keller
 *
 *  Copyright 2014 University of Zurich
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.signalcollect.sna.gephiconnectors;

import java.util.Map;
import java.util.TreeMap;

import com.signalcollect.sna.ClusterDistribution;
import com.signalcollect.sna.ExecutionResult;
import com.signalcollect.sna.GraphProperties;
import com.signalcollect.sna.constants.SNAClassNames;
import com.signalcollect.sna.metrics.LocalClusterCoefficient;

public class LocalClusterCoefficientSignalCollectGephiConnectorImpl extends
		SignalCollectGephiConnector {

	private ExecutionResult localClusterCoefficientResult;
	private GraphProperties graphProps;
	private ClusterDistribution clusterDistribution;

	public LocalClusterCoefficientSignalCollectGephiConnectorImpl(
			String fileName) {
		super(fileName, SNAClassNames.LOCALCLUSTERCOEFFICIENT);
	}

	@Override
	public void executeGraph() {
		if (localClusterCoefficientResult == null) {
			localClusterCoefficientResult = LocalClusterCoefficient
					.run(getGraph());
		}
	}

	@Override
	public double getAverage() {
		if (localClusterCoefficientResult == null) {
			executeGraph();
		}
		return localClusterCoefficientResult.compRes().average();
	}

	@Override
	public Map<String, Object> getAll() {
		if (localClusterCoefficientResult == null) {
			executeGraph();
		}
		TreeMap<String, Object> result = new TreeMap<String, Object>(
				new NumbersThenWordsComparator());
		result.putAll(localClusterCoefficientResult.compRes().vertexMap());
		return result;
	}

	@Override
	public GraphProperties getGraphProperties() {
		if (localClusterCoefficientResult == null) {
			executeGraph();
		}
		graphProps = new GraphProperties(
				localClusterCoefficientResult.vertexArray(), getFileName());
		return graphProps;
	}

	@Override
	public Map<Double, Integer> getClusterDistribution() {
		if (localClusterCoefficientResult == null) {
			executeGraph();
		}
		clusterDistribution = new ClusterDistribution(getFileName());
		clusterDistribution.setClusterMap(localClusterCoefficientResult
				.compRes().vertexMap());

		return clusterDistribution.gatherClusterDistribution();
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		SignalCollectGephiConnector a = new LocalClusterCoefficientSignalCollectGephiConnectorImpl(
				"/Users/flaviokeller/Desktop/examplegraph_separated.gml");
		a.executeGraph();
		double d = a.getAverage();
		Map<String, Object> l = a.getAll();
		long intermediate = System.currentTimeMillis();
		double intermediateTime = Double.valueOf(intermediate - startTime) / 1000d;
		System.out.println("execution time: " + intermediateTime + " seconds");

		GraphProperties p = a.getGraphProperties();
		intermediate = System.currentTimeMillis();
		intermediateTime = Double.valueOf(intermediate - startTime) / 1000d;
		System.out.println("properties time: " + intermediateTime + " seconds");

		Map<Integer, Integer> dd = a.getDegreeDistribution();
		Map<Double, Integer> cd = a.getClusterDistribution();

		intermediate = System.currentTimeMillis();
		intermediateTime = Double.valueOf(intermediate - startTime) / 1000d;
		System.out.println("elapsed time until image creation: "
				+ intermediateTime + " seconds");

		try {
			a.createDegreeDistributionImageFile(dd, "degreeDistr.png");
			a.createClusterDistributionImageFile(cd, "clusterdistr.png");
			long stopTime = System.currentTimeMillis();
			double elapsedTime = Double.valueOf(stopTime - startTime) / 1000d;
			System.out.println("full elapsed time: " + elapsedTime
					+ " seconds\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("The average local cluster coefficient is: " + d);
		System.out
				.println("The single vertex local cluster coefficient values are: "
						+ l);
		System.out.println(p);
		System.out.println("The degree distribution is: " + dd);
		System.out.println("The local cluster coefficient distribution is: "
				+ cd);
	}
}
