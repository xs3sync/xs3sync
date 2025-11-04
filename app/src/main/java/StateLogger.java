import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StateLogger {
    private List<Task> tasks = new ArrayList<>();

    public void setTask(
        final @Nonnull String id,
        final @Nonnull String name,
        final @Nonnull Integer progress
    ) {
        @Nullable Task task = tasks.stream()
            .filter(task1 -> task1.id.equals(name))
            .findFirst().orElse(null);

        if (task == null) {
            task = new Task(id, name, progress);
            tasks.add(task);
        } else {
            task.progress = progress;
        }
    }

    public void print() {

    }

    private static class Task {
        private @Nonnull String id;
        private @Nonnull String name;
        private @Nonnull Integer progress;

        public Task(
            final @Nonnull String id,
            final @Nonnull String name,
            final @Nonnull Integer progress
        ) {
            this.id = id;
            this.name = name;
            this.progress = progress;
        }
    }
}
