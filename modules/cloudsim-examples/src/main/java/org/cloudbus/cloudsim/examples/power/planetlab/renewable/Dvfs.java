package org.cloudbus.cloudsim.examples.power.planetlab.renewable;

import org.cloudbus.cloudsim.examples.power.planetlab.PlanetLabRunner;
import org.cloudbus.cloudsim.power.BSRNDataLoader;
import org.cloudbus.cloudsim.power.PhotovoltaicFarm;
import org.cloudbus.cloudsim.power.RenewableEnergySource;

import java.io.IOException;

/**
 * A simulation of a heterogeneous power aware data center that only applied DVFS, but no dynamic
 * optimization of the VM allocation. The adjustment of the hosts' power consumption according to
 * their CPU utilization is happening in the PowerDatacenter class.
 * 
 * This example uses a real PlanetLab workload: 20110303.
 * 
 * The remaining configuration parameters are in the Constants and PlanetLabConstants classes.
 * 
 * If you are using any algorithms, policies or workload included in the power package please cite
 * the following paper:
 * 
 * Anton Beloglazov, and Rajkumar Buyya, "Optimal Online Deterministic Algorithms and Adaptive
 * Heuristics for Energy and Performance Efficient Dynamic Consolidation of Virtual Machines in
 * Cloud Data Centers", Concurrency and Computation: Practice and Experience (CCPE), Volume 24,
 * Issue 13, Pages: 1397-1420, John Wiley & Sons, Ltd, New York, USA, 2012
 * 
 * @author Anton Beloglazov
 * @since Jan 5, 2012
 */
public class Dvfs {

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException, Exception {
		boolean enableOutput = true;
		boolean outputToFile = false;
		String inputFolder = Dvfs.class.getClassLoader().getResource("workload/planetlab").getPath().replace("%20", " ");
		String outputFolder = "output";
		String workload = "20110420"; // PlanetLab workload
		String vmAllocationPolicy = "dvfs"; // DVFS policy without VM migrations
		String vmSelectionPolicy = "";
		String parameter = "";

		BSRNDataLoader bsrnDataLoader = null;
		try {
			bsrnDataLoader = new BSRNDataLoader(
					"BUD_radiation_2020-07.tab"
			);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Couldn't initialize BSRNDataLoader object.");
		}

		// Wcześniej był tu odpowiednik
		// RenewableEnergySource renewableEnergySource = new RenewableEnergySource(new PhotovoltaicFarm(12,2,0.5,0.5), bsrnDataLoader);
		RenewableEnergySource renewableEnergySource = new RenewableEnergySource(new PhotovoltaicFarm(1,1,1,0.00002), bsrnDataLoader);

		new PlanetLabRunner(
				enableOutput,
				outputToFile,
				inputFolder,
				outputFolder,
				workload,
				vmAllocationPolicy,
				vmSelectionPolicy,
				parameter,
				renewableEnergySource);
	}
}
