package data;

import models.GraphEdge;
import models.GraphNode;
import play.Logger;

import javax.inject.Singleton;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;


@Singleton
public class FileSystemDAO {

    private static final String NODE_DATA_FILE_DIR = "./resources/OECD-WTO_BATIS_codes.csv";

    private static final String EDGE_DATA_FILE_DIR = "./resources/OECD-WTO_BATIS_data.csv";

    public Set<GraphNode> readNodes() {
        Logger.info("Started reading from file");
        final Set<GraphNode> result = new HashSet<>();
        final long startTime = System.currentTimeMillis();

        try (final BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(NODE_DATA_FILE_DIR), StandardCharsets.UTF_8)) {
            String line = bufferedReader.readLine();

            while(line!=null) {
                final String[] valuesArray = line.split(",", -1);
                result.add(GraphNode.from(valuesArray[0], valuesArray[1]));
                line = bufferedReader.readLine();
            }

        } catch (final IOException exception) {
            Logger.error("There was an error while opening the file: " + NODE_DATA_FILE_DIR);
        } finally {
            final long endTime = System.currentTimeMillis();
            Logger.info("Finished reading nodes from the file! It took: " + Long.toString(endTime - startTime) + " milliseconds");
        }

        return result;
    }

    public Set<GraphEdge> readEdges(final int startLine, final int endLine) {

        Logger.info("Started reading from file");
        final Set<GraphEdge> result = new HashSet<>();
        final long startTime = System.currentTimeMillis();

        try (final BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(EDGE_DATA_FILE_DIR), StandardCharsets.UTF_8)) {
            String line = bufferedReader.readLine();
            int lineNumber = 1;

            while(line!=null) {
                if( lineNumber >= startLine && lineNumber <= endLine ) {
                    final String[] valuesArray = line.split(",", -1);
                    result.add(GraphEdge.from(valuesArray));
                }
                if( lineNumber > endLine ) {
                    break;
                }
                line = bufferedReader.readLine();
                lineNumber++;
            }

        } catch (final IOException exception) {
            Logger.error("There was an error while opening the file: " + EDGE_DATA_FILE_DIR);
        } finally {
            final long endTime = System.currentTimeMillis();
            Logger.info("Finished reading edges from the file! It took: " + Long.toString(endTime - startTime) + " milliseconds");
        }

        return result;
    }

    public int numberOfEdges() {
        try(final LineNumberReader lineNumberReader = new LineNumberReader( new FileReader( EDGE_DATA_FILE_DIR ))) {

            while(lineNumberReader.readLine()!= null) {}
            return lineNumberReader.getLineNumber();

        }
        catch(final IOException exc) {
            Logger.error("Faield to read the number of lines in the file with {}", exc);
            return -1;
        }
    }

}
