import skadistats.clarity.Clarity;
import skadistats.clarity.model.Entity;
import skadistats.clarity.model.FieldPath;
import skadistats.clarity.processor.entities.OnEntityUpdated;
import skadistats.clarity.processor.runner.Context;
import skadistats.clarity.processor.runner.SimpleRunner;
import skadistats.clarity.source.MappedFileSource;
import skadistats.clarity.wire.common.proto.Demo.CDemoFileInfo;

import java.io.File;
import java.io.IOException;

public class Main {

    int count = 0;

    @OnEntityUpdated
    public void onUpdated(Context ctx, Entity e, FieldPath[] updatedPaths, int updateCount) {
        count += 1;
    }

    public void run(String replayPath) throws Exception {
        count = 0;
        new SimpleRunner(new MappedFileSource(replayPath)).runWith(this);
        System.out.println(count);
    }

    public static void main(String[] args) throws Exception {
        File dir  = new File("replays");
        File[] replays = dir.listFiles();
        for (File replay : replays) {
            String replayPath = replay.getCanonicalPath();
            CDemoFileInfo info = Clarity.infoForFile(replayPath);
            new Main().run(replayPath);
        }
    }

}
