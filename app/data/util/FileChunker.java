package data.util;

import play.Logger;

import java.util.function.BiConsumer;

public class FileChunker {

    /**
     * The chunk size in lines
     */
    private static int CHUNK_SIZE = 100000;

    private final int linesInFile;

    private FileChunker(final int linesInFile) {
        this.linesInFile = linesInFile;
    }

    public static FileChunker from( final int linesInFile ) {
        final FileChunker chunker = new FileChunker(linesInFile);

        return chunker;
    }

    private int getNumberOfChunks() {
        return (int) Math.ceil( linesInFile / (double) CHUNK_SIZE );
    }

    public void forEachChunk(final BiConsumer<Integer, Integer> doWithChunk) {

        final int numberOfChunks = getNumberOfChunks();

        if(numberOfChunks == 0) {
            throw new RuntimeException("This file shouldn't be chunked!");
        }

        int chunkCounter = 1;

        while(chunkCounter <= numberOfChunks) {
            Logger.info("Chunk [{}/{}]", chunkCounter, numberOfChunks);
            final int lastLine = chunkCounter * CHUNK_SIZE;
            final int firstLine = lastLine - CHUNK_SIZE + 1;
            doWithChunk.accept(firstLine, lastLine);
            chunkCounter++;
        }

    }

}