
package org.encog.neural.persist;

//import java.io.Serializable;

/**
 * Dummy class to allow compilation under Java ME
 *
 * @author barbeau
 *
 */
public interface EncogPersistedObject {
	public void setDescription(String theDescription);
	public String getDescription();
	
	public void setName(String theName);
	public String getName();
	public Persistor createPersistor();
}
