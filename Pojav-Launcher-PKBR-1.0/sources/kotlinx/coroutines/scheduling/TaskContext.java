package kotlinx.coroutines.scheduling;

import kotlin.Metadata;

@Metadata(mo11814d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b`\u0018\u00002\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0007H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\b"}, mo11815d2 = {"Lkotlinx/coroutines/scheduling/TaskContext;", "", "taskMode", "", "getTaskMode", "()I", "afterTask", "", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: Tasks.kt */
public interface TaskContext {
    void afterTask();

    int getTaskMode();
}
