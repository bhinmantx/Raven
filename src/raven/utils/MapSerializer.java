/**
 * Static class that serializes and deserializes maps for our use.
 */
package raven.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import raven.game.interfaces.IRavenMap;
import raven.game.model.RavenBot;
import raven.game.model.RavenDoor;
import raven.game.model.RavenMap;
import raven.game.triggers.Trigger;
import raven.game.triggers.TriggerHealthGiver;
import raven.game.triggers.TriggerRegionCircle;
import raven.game.triggers.TriggerRegionRectangle;
import raven.game.triggers.TriggerSystem;
import raven.game.triggers.TriggerWeaponGiver;
import raven.math.CellSpacePartition;
import raven.math.Vector2D;
import raven.math.Wall2D;
import raven.math.graph.SparseGraph;
import raven.navigation.model.NavGraphEdge;
import raven.navigation.model.NavGraphNode;

import com.thoughtworks.xstream.XStream;


/**
 * @author chester
 *
 */
public class MapSerializer {
	
	protected static XStream initXStream() {
		XStream streamer = new XStream();
		
		//setup aliases to prevent fully qualified autogens, this is a purely cosmetic change
		streamer.processAnnotations(RavenMap.class);
		streamer.processAnnotations(Wall2D.class);
		streamer.processAnnotations(RavenBot.class);
		streamer.processAnnotations(Vector2D.class);
		streamer.processAnnotations(RavenDoor.class);
		streamer.processAnnotations(SparseGraph.class);
		streamer.processAnnotations(Trigger.class);
		streamer.processAnnotations(TriggerSystem.class);
		streamer.processAnnotations(TriggerRegionCircle.class);
		streamer.processAnnotations(TriggerRegionRectangle.class);
		streamer.processAnnotations(TriggerHealthGiver.class);
		streamer.processAnnotations(TriggerWeaponGiver.class);
		streamer.processAnnotations(NavGraphNode.class);
		streamer.processAnnotations(NavGraphEdge.class);
		
		return streamer;
	}
	
	public static String serializeMap(IRavenMap map) {
		return initXStream().toXML(map);
	}
	
	public static boolean serializeMapToFile(IRavenMap map, File file) throws IOException {
		FileWriter writer = new FileWriter(file);
		writer.write(initXStream().toXML(map));
		writer.close();
		return true;
	}
	
	public static boolean serializeMapToPath(IRavenMap map, String filePath) throws IOException {
		FileWriter writer = new FileWriter(filePath);
		writer.write(initXStream().toXML(map));
		writer.close();
		return true;
	}
	
	public static IRavenMap deserializeMapFromXML(String xml){
		return (IRavenMap) initXStream().fromXML(xml);
	}
	
	public static IRavenMap deserializeMapFromFile(File file) throws IOException {
		FileReader reader = new FileReader(file);
		IRavenMap result = (IRavenMap)initXStream().fromXML(reader);
		reader.close();
		return result;
	}
	
	public static IRavenMap deserializeMapFromPath(String filePath) throws IOException {
		FileReader reader = new FileReader(filePath);
		IRavenMap result = (IRavenMap)initXStream().fromXML(reader);
		reader.close();
		return result;
	}
}
