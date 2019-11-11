package info.esblurock.reaction.core.ontology.base.generation;

public interface StandardGenerationInterface {

	public String stringCreateObject();
	public String stringCreateYamlObject();
	public String stringCreateSetObject(String infoname, boolean yaml);
	public String stringCreateYamlFromObject();
	public String retrieveName();
	public String toString();
	public String toString(String prefix);
	public String getHierarchyName();
	public String getClassName();
}
