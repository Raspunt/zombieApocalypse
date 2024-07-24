package org.giga.zombieapocalypse.process;

import net.minecraft.server.MinecraftServer;

import java.util.PriorityQueue;

public class TickTaskManager {
    protected MinecraftServer server;
    private final PriorityQueue<TickTaskTimer> tasks;

    public TickTaskManager(MinecraftServer server) {
        this.server = server;
        this.tasks = new PriorityQueue<>();
    }

    public synchronized void tick() {
        long currentTick = server.getTicks();
        while (!tasks.isEmpty() && tasks.peek().scheduledTick <= currentTick) {
            TickTaskTimer task = tasks.poll(); // Извлечение и удаление задачи из очереди
            if (task.callback != null) {
                task.callback.onTimeout(); // Выполнение задачи
            }
        }
    }

    public synchronized TickTaskTimer timer(Task callback, long delayTicks) {
        TickTaskTimer task = new TickTaskTimer(this, delayTicks, callback);
        tasks.add(task);
        return task;
    }

    public interface Task {
        void onTimeout();
    }

    public class TickTaskTimer implements Comparable<TickTaskTimer> {
        private TickTaskManager manager;
        protected long delay;
        protected long scheduledTick;
        protected Task callback;

        TickTaskTimer(TickTaskManager manager, long delay, Task callback) {
            this.manager = manager;
            this.delay = delay;
            this.callback = callback;
            this.scheduledTick = manager.server.getTicks() + delay;
        }

        @Override
        public int compareTo(TickTaskTimer other) {
            return Long.compare(this.scheduledTick, other.scheduledTick);
        }

        public synchronized boolean remove() {
            return manager.tasks.remove(this);
        }

        public long getRemainingTicks() {
            long currentTick = manager.server.getTicks();
            return scheduledTick - currentTick;
        }

        public synchronized void restart(long newDelayTicks) {
            manager.tasks.remove(this);
            this.delay = newDelayTicks;
            this.updateScheduledTick();
            manager.tasks.add(this);
        }

        public synchronized void restart() {
            manager.tasks.remove(this);
            this.updateScheduledTick();
            manager.tasks.add(this);
        }

        public synchronized void updateDelay(long delay) {
            manager.tasks.remove(this);
            long updated = this.scheduledTick - this.delay;
            this.scheduledTick = updated + delay;
            manager.tasks.add(this);
        }

        private void updateScheduledTick() {
            this.scheduledTick = manager.server.getTicks() + this.delay;
        }

    }
}