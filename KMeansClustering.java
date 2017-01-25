import java.util.*;
import java.lang.Math;

/* 
 * Standard implementation of k-means clustering.
 */
public class KMeansClustering {
	public static double weightedAverage(HashMap<Integer, Integer> hmap) {
		if (hmap==null) return 0;
		int total=0;
		for (int occurrence : hmap.values()) {
			total+=occurrence;
		}
		double sum=0;
		for (int number : hmap.keySet()) {
			sum+=number*hmap.get(number);
		}
		return sum/total;
	}
	
	public static int determineCluster(int num, ArrayList<Double> means) {
		
		int cluster=0;
		for (int i=1; i<means.size(); i++) {
			if (Math.abs(num-means.get(i))<Math.abs(num-means.get(cluster))) {
				cluster=i;
			}
		}
		
		return cluster;
	}
	
	public static boolean compareClusters(
			ArrayList<HashMap<Integer, Integer>> listCluster1,
				ArrayList<HashMap<Integer, Integer>> listCluster2) {
		boolean equal=true;
		for (int i=0; i<listCluster1.size(); i++) {
			if (!listCluster1.get(i).keySet().equals(
								listCluster2.get(i).keySet())) {
				equal=false;
			}
		}
		return equal;
	}
	
	public static ArrayList<Double> kMeans(HashMap<Integer, Integer> map) {
		ArrayList<HashMap<Integer,Integer>> listCluster = new ArrayList<>();
		for (int i=0; i<3; i++) {
			listCluster.add(new HashMap<Integer,Integer>());
		}
		
		ArrayList<Double> meansValue = new ArrayList<>();
		double minTokenValue = Collections.min(map.keySet());
		meansValue.add(minTokenValue);
		meansValue.add(minTokenValue*3);
		meansValue.add(minTokenValue*7);
		
		boolean finish= false;
		while(!finish) {
			ArrayList<HashMap<Integer,Integer>> listClusterNew = new ArrayList<>();
			for (int i=0; i<3; i++) {
				listClusterNew.add(new HashMap<Integer,Integer>());
			}
			
			for (int tokenValue : map.keySet()) {
				int clusterNumber = determineCluster(tokenValue, meansValue);
				listClusterNew.get(clusterNumber).put(
											tokenValue, map.get(tokenValue));
			}
			
			for (int i=0; i<3; i++) {
				meansValue.set(i, weightedAverage(listClusterNew.get(i)));
			}
			
			finish=compareClusters(listCluster, listClusterNew);
			listCluster = listClusterNew;
		}
		return meansValue;
	}

}
