package com.pict.tia.core;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DoxygenRunner d = new DoxygenRunner();
		DependencyFinder df = new DependencyFinder();
		RepositoryAnalyzer ra = new RepositoryAnalyzer();

		int err = 0;
		err = d.runDoxygen();

		if (err == 0) {
			System.out.println("Doxygen Command Executed Succesfully");
			err = df.findDependencies();

			if (err == 0) {
				System.out.println("Dependencies Found Successfully");
				err = ra.findGitHistory();

				if (err == 0) {
					System.out.println("Git History Found Successfully");
				} else {
					System.out.println("Could not find Git History, please try again.");
				}

			} else {
				System.out.println("Could not find function dependencies, please try again\n");

			}
		} else {
			System.out.println("Could not generate doxygen documentation, please try again\n");
		}
	}

}
