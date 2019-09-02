package info.esblurock.reaction.chemconnect.core.base.client.catalog;

public interface SetUpCollapsibleItemInterface {
	
	public abstract void addInformation(StandardDatasetObjectHierarchyItem item);

	public abstract boolean update(StandardDatasetObjectHierarchyItem item);

	public abstract int priority();

	public abstract boolean isInformation();

	public abstract boolean addSubitems();

}
