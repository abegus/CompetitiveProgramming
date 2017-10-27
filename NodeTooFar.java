import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class NodeTooFar {
	public static int totalNodes;
	public static HashSet<Integer> seenSet;

	public static void main(String[] args) throws IOException{
		Scanner scan = new Scanner(System.in);
		//Scanner scan = new Scanner(new File("testfile.txt"));
		int caseNum = 0;
		seenSet = new HashSet<Integer>();

		//continuous structure
		while(scan.hasNext()) {
			int numConnections = scan.nextInt();
			if(numConnections == 0) {
				break; //end case
			}

			HashMap<Integer, ArrayList<Integer>> connections = new HashMap<>();// store connections   35: [15, 40, 55]

			for(int x = 0; x < numConnections; x++) {
				int first = scan.nextInt();
				int second = scan.nextInt();

				//CASES FOR INSERTING CONNECTIONS
				if(connections.containsKey(first)) {
					connections.get(first).add(second);
				}
				else {
					ArrayList<Integer> li = new ArrayList<Integer>();
					li.add(second);
					connections.put(first, li);
				}
				if(connections.containsKey(second)) {
					connections.get(second).add(first);
				}
				else {
					ArrayList<Integer> li = new ArrayList<Integer>();
					li.add(first);
					connections.put(second, li);
				}
			}


			while(scan.hasNext()) { //query section, terminated by 0 0
				int startNode = scan.nextInt();
				int ttl = scan.nextInt();
				if(startNode == 0 && ttl == 0) { //end case
					break;
				}
				caseNum++;
				totalNodes = connections.size();

				seenSet.clear();
				seenSet.add(startNode);
				recursive(connections, ttl, startNode, startNode); 

				int unreach = totalNodes - seenSet.size();
				System.out.println("Case " + caseNum + ": " + unreach + " nodes not reachable from node " + startNode+" with TTL = "+ttl+".");
			}
		}

	}


	public static void recursive( HashMap<Integer, ArrayList<Integer>> connections, int ttl, int currentNode, int initialNode){   //, HashSet<Integer> seenSet) {
		//base case, -1 ttl
		if(ttl < 0) {
			return ;
		}

		//update case
		ttl--;
		seenSet.add(currentNode);

		//recursive send to all nodes attached
		//HashSet<Integer> retSet = new HashSet<Integer>();
		for(Integer i : connections.get(currentNode)) {
			if(!seenSet.contains(i))
			{
				recursive(connections,ttl,i,initialNode) ;
				//System.out.println(i);
			}
		}

		return ;
	}

}
