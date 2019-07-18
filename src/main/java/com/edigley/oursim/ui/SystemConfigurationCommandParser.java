/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.edigley.oursim.ui;

import static com.edigley.oursim.ui.CLIUTil.showMessageAndExit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import com.edigley.oursim.entities.Grid;
import com.edigley.oursim.entities.Machine;
import com.edigley.oursim.entities.Peer;
import com.edigley.oursim.entities.Processor;
import com.edigley.oursim.policy.ResourceSharingPolicy;

public class SystemConfigurationCommandParser {

	public static Grid readPeersDescription(File siteDescription, File machinesDescription, ResourceSharingPolicy sharingPolicy) throws FileNotFoundException {
		Grid grid = new Grid();
		Scanner scPeers = new Scanner(siteDescription);
		scPeers.nextLine(); // skip header
		while (scPeers.hasNextLine()) {
			Scanner scLine = new Scanner(scPeers.nextLine());
			String peerName = scLine.next();
			if (!grid.containsPeer(peerName)) {
				grid.addPeer(new Peer(peerName, sharingPolicy));
			} else {
				showMessageAndExit("Já foi adicionado um peer com esse nome: " + peerName);
			}
			scLine.close();
		}
		scPeers.close();

		Scanner scMachines = new Scanner(machinesDescription);
		scMachines.nextLine(); // skip header
		while (scMachines.hasNextLine()) {
			Scanner scLine = new Scanner(scMachines.nextLine());
			scLine.useLocale(Locale.US);
			String machineName = scLine.next();
			Double machineSpeed = scLine.nextDouble();
			String sourcePeerName = scLine.next();
			if (grid.containsPeer(sourcePeerName)) {
				if (!grid.getPeer(sourcePeerName).hasMachine(machineName)) {
					grid.getPeer(sourcePeerName).addMachine(new Machine(machineName, Processor.convertGHz2Mips(machineSpeed)));
				} else {
					showMessageAndExit("Já existe uma máquina com o mesmo nome: " + machineName);
				}
			} else {
				showMessageAndExit("A máquina " + machineName + " pertence a um Peer que não existe: " + sourcePeerName);
			}
			scLine.close();
		}
		scMachines.close();
		return grid;

	}

	public static Grid readPeersDescription(File siteDescription, int numberOfResourcesByPeer, ResourceSharingPolicy sharingPolicy)
			throws FileNotFoundException {
		Grid grid = new Grid();
		Scanner sc = new Scanner(siteDescription);
		sc.nextLine(); // skip header
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			String peerName = scLine.next();
			int peerSize = scLine.nextInt();
			if (!grid.containsPeer(peerName)) {
				Peer peer = (numberOfResourcesByPeer > 0) ? new Peer(peerName, numberOfResourcesByPeer, sharingPolicy) : new Peer(peerName, sharingPolicy);
				if (numberOfResourcesByPeer == 0) {
					for (int i = 0; i < peerSize; i++) {
						// String machineFullName = peer.getName() + ".m_" + i;
						String machineFullName = peer.getMachineName(i);
						peer.addMachine(new Machine(machineFullName, Processor.EC2_COMPUTE_UNIT.getSpeed()));
					}
				}
				grid.addPeer(peer);
			} else {
				showMessageAndExit("Já foi adicionado um peer com esse nome: " + peerName);
			}
			scLine.close();
		}
		sc.close();
		return grid;
	}

	@Deprecated
	public static void addResourcesToPeers(Map<String, Peer> peersMap, String peersDescriptionFilePath) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(peersDescriptionFilePath));
		sc.nextLine();// desconsidera o cabeçalho
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			String peerName = scLine.next();
			if (peersMap.containsKey(peerName)) {
				int peerSize = scLine.nextInt();
				Peer peer = peersMap.get(peerName);
				for (int i = 0; i < peerSize; i++) {
					String machineFullName = peer.getName() + "_m_" + i;
					peer.addMachine(new Machine(machineFullName, Processor.EC2_COMPUTE_UNIT.getSpeed()));
				}
			}
			scLine.close();
		}
		sc.close();
	}

}
