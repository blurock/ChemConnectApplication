package info.esblurock.ChemConnectGeneratedArtifacts;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import info.esblurock.ChemConnectGeneratedArtifacts.utils.GeneratePackageInformation;

public class GeneratePomFile {

	@Test
	public void test() {
		
		String submodule = "dataset:ChemConnectBaseModule";
		String module = "dataset:ChemConnectExpDataModule";
		
		System.out.println("Find dependent modules");
		
		String pomS;
		try {
			pomS = GeneratePackageInformation.generatePom(module);
			System.out.println(pomS);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
