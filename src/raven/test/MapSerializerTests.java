/**
 * 
 */
package raven.test;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import raven.game.interfaces.IRavenMap;
import raven.game.model.RavenMap;
import raven.utils.MapSerializer;

/**
 * @author chester
 *
 */
public class MapSerializerTests {

	private String emptyString = 
	"<RavenMap>\n" +
	"  <walls/>\n" +
	"  <triggerSystem/>\n" +
	"  <spawnPoints/>\n" +
	"  <doors/>\n" +
	"  <navGraph>\n" +
	"    <nodes/>\n" +
	"    <edges/>\n" +
	"    <isDigraph>false</isDigraph>\n" +
	"    <nextNodeIndex>0</nextNodeIndex>\n" +
	"  </navGraph>\n" +
	"  <sizeX>500</sizeX>\n" +
	"  <sizeY>500</sizeY>\n" +
	"</RavenMap>";
	
	/*
	 * Create an empty Map and deserialize it.
	 */
	@Test
	public void DeserializeEmptyMap() {	
		IRavenMap writeMe = CreateEmptyMap();
		String emptyMap = MapSerializer.serializeMap(writeMe);
		Assert.assertEquals(emptyString, emptyMap);
	}
	
	@Test
	@Ignore
	public void SerializedEmptyMapEqualsNewEmptyMap(){
		IRavenMap expected = CreateEmptyMap();
		IRavenMap actual = MapSerializer.deserializeMapFromXML(emptyString);
		Assert.assertEquals(expected, actual);
	}
	
	private IRavenMap CreateEmptyMap() {
		return new RavenMap();
	}
}
