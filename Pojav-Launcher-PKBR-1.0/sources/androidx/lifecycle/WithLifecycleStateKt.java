package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000,\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u001aA\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a+\u0010\f\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\r\u001a+\u0010\f\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u000e2\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a+\u0010\u0010\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\r\u001a+\u0010\u0010\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u000e2\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a+\u0010\u0011\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\r\u001a+\u0010\u0011\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u000e2\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a3\u0010\u0012\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001a3\u0010\u0012\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00042\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a3\u0010\u0015\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\u0013\u0002\u0004\n\u0002\b\u0019¨\u0006\u0016"}, mo11815d2 = {"suspendWithStateAtLeastUnchecked", "R", "Landroidx/lifecycle/Lifecycle;", "state", "Landroidx/lifecycle/Lifecycle$State;", "dispatchNeeded", "", "lifecycleDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "block", "Lkotlin/Function0;", "(Landroidx/lifecycle/Lifecycle;Landroidx/lifecycle/Lifecycle$State;ZLkotlinx/coroutines/CoroutineDispatcher;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withCreated", "(Landroidx/lifecycle/Lifecycle;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Landroidx/lifecycle/LifecycleOwner;", "(Landroidx/lifecycle/LifecycleOwner;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withResumed", "withStarted", "withStateAtLeast", "(Landroidx/lifecycle/Lifecycle;Landroidx/lifecycle/Lifecycle$State;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Landroidx/lifecycle/LifecycleOwner;Landroidx/lifecycle/Lifecycle$State;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withStateAtLeastUnchecked", "lifecycle-runtime-ktx_release"}, mo11816k = 2, mo11817mv = {1, 4, 1})
/* compiled from: WithLifecycleState.kt */
public final class WithLifecycleStateKt {
    public static final <R> Object withStateAtLeast(Lifecycle $this$withStateAtLeast, Lifecycle.State state, Function0<? extends R> block, Continuation<? super R> $completion) {
        if (state.compareTo(Lifecycle.State.CREATED) >= 0) {
            Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withStateAtLeast;
            MainCoroutineDispatcher lifecycleDispatcher$iv = Dispatchers.getMain().getImmediate();
            boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded($completion.getContext());
            if (!dispatchNeeded$iv) {
                if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                    throw new LifecycleDestroyedException();
                } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state) >= 0) {
                    return block.invoke();
                }
            }
            return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
        }
        throw new IllegalArgumentException(("target state must be CREATED or greater, found " + state).toString());
    }

    private static final Object withStateAtLeast$$forInline(Lifecycle $this$withStateAtLeast, Lifecycle.State state, Function0 block, Continuation continuation) {
        if (state.compareTo(Lifecycle.State.CREATED) >= 0) {
            Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withStateAtLeast;
            MainCoroutineDispatcher lifecycleDispatcher$iv = Dispatchers.getMain().getImmediate();
            InlineMarker.mark(3);
            Continuation continuation2 = null;
            boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded(continuation2.getContext());
            if (!dispatchNeeded$iv) {
                if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                    throw new LifecycleDestroyedException();
                } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state) >= 0) {
                    return block.invoke();
                }
            }
            InlineMarker.mark(0);
            Object suspendWithStateAtLeastUnchecked = suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), continuation);
            InlineMarker.mark(1);
            return suspendWithStateAtLeastUnchecked;
        }
        throw new IllegalArgumentException(("target state must be CREATED or greater, found " + state).toString());
    }

    public static final <R> Object withCreated(Lifecycle $this$withCreated, Function0<? extends R> block, Continuation<? super R> $completion) {
        Lifecycle.State state$iv = Lifecycle.State.CREATED;
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withCreated;
        MainCoroutineDispatcher lifecycleDispatcher$iv = Dispatchers.getMain().getImmediate();
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded($completion.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
    }

    private static final Object withCreated$$forInline(Lifecycle $this$withCreated, Function0 block, Continuation continuation) {
        Lifecycle.State state$iv = Lifecycle.State.CREATED;
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withCreated;
        MainCoroutineDispatcher lifecycleDispatcher$iv = Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        Continuation continuation2 = null;
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded(continuation2.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        InlineMarker.mark(0);
        Object suspendWithStateAtLeastUnchecked = suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), continuation);
        InlineMarker.mark(1);
        return suspendWithStateAtLeastUnchecked;
    }

    public static final <R> Object withStarted(Lifecycle $this$withStarted, Function0<? extends R> block, Continuation<? super R> $completion) {
        Lifecycle.State state$iv = Lifecycle.State.STARTED;
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withStarted;
        MainCoroutineDispatcher lifecycleDispatcher$iv = Dispatchers.getMain().getImmediate();
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded($completion.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
    }

    private static final Object withStarted$$forInline(Lifecycle $this$withStarted, Function0 block, Continuation continuation) {
        Lifecycle.State state$iv = Lifecycle.State.STARTED;
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withStarted;
        MainCoroutineDispatcher lifecycleDispatcher$iv = Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        Continuation continuation2 = null;
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded(continuation2.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        InlineMarker.mark(0);
        Object suspendWithStateAtLeastUnchecked = suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), continuation);
        InlineMarker.mark(1);
        return suspendWithStateAtLeastUnchecked;
    }

    public static final <R> Object withResumed(Lifecycle $this$withResumed, Function0<? extends R> block, Continuation<? super R> $completion) {
        Lifecycle.State state$iv = Lifecycle.State.RESUMED;
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withResumed;
        MainCoroutineDispatcher lifecycleDispatcher$iv = Dispatchers.getMain().getImmediate();
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded($completion.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
    }

    private static final Object withResumed$$forInline(Lifecycle $this$withResumed, Function0 block, Continuation continuation) {
        Lifecycle.State state$iv = Lifecycle.State.RESUMED;
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withResumed;
        MainCoroutineDispatcher lifecycleDispatcher$iv = Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        Continuation continuation2 = null;
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded(continuation2.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        InlineMarker.mark(0);
        Object suspendWithStateAtLeastUnchecked = suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), continuation);
        InlineMarker.mark(1);
        return suspendWithStateAtLeastUnchecked;
    }

    public static final <R> Object withStateAtLeast(LifecycleOwner $this$withStateAtLeast, Lifecycle.State state, Function0<? extends R> block, Continuation<? super R> $completion) {
        Lifecycle $this$withStateAtLeast$iv = $this$withStateAtLeast.getLifecycle();
        Intrinsics.checkNotNullExpressionValue($this$withStateAtLeast$iv, "lifecycle");
        if (state.compareTo(Lifecycle.State.CREATED) >= 0) {
            Lifecycle $this$withStateAtLeastUnchecked$iv$iv = $this$withStateAtLeast$iv;
            MainCoroutineDispatcher lifecycleDispatcher$iv$iv = Dispatchers.getMain().getImmediate();
            boolean dispatchNeeded$iv$iv = lifecycleDispatcher$iv$iv.isDispatchNeeded($completion.getContext());
            if (!dispatchNeeded$iv$iv) {
                if ($this$withStateAtLeastUnchecked$iv$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                    throw new LifecycleDestroyedException();
                } else if ($this$withStateAtLeastUnchecked$iv$iv.getCurrentState().compareTo(state) >= 0) {
                    return block.invoke();
                }
            }
            return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv$iv, state, dispatchNeeded$iv$iv, lifecycleDispatcher$iv$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
        }
        throw new IllegalArgumentException(("target state must be CREATED or greater, found " + state).toString());
    }

    private static final Object withStateAtLeast$$forInline(LifecycleOwner $this$withStateAtLeast, Lifecycle.State state, Function0 block, Continuation continuation) {
        Lifecycle.State state2 = state;
        Lifecycle lifecycle = $this$withStateAtLeast.getLifecycle();
        Intrinsics.checkNotNullExpressionValue(lifecycle, "lifecycle");
        Lifecycle $this$withStateAtLeast$iv = lifecycle;
        if (state2.compareTo(Lifecycle.State.CREATED) >= 0) {
            Lifecycle $this$withStateAtLeastUnchecked$iv$iv = $this$withStateAtLeast$iv;
            MainCoroutineDispatcher lifecycleDispatcher$iv$iv = Dispatchers.getMain().getImmediate();
            InlineMarker.mark(3);
            Continuation continuation2 = null;
            boolean dispatchNeeded$iv$iv = lifecycleDispatcher$iv$iv.isDispatchNeeded(continuation2.getContext());
            if (!dispatchNeeded$iv$iv) {
                if ($this$withStateAtLeastUnchecked$iv$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                    throw new LifecycleDestroyedException();
                } else if ($this$withStateAtLeastUnchecked$iv$iv.getCurrentState().compareTo(state2) >= 0) {
                    Function0 function0 = block;
                    return block.invoke();
                }
            }
            InlineMarker.mark(0);
            Object suspendWithStateAtLeastUnchecked = suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv$iv, state, dispatchNeeded$iv$iv, lifecycleDispatcher$iv$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), continuation);
            InlineMarker.mark(1);
            return suspendWithStateAtLeastUnchecked;
        }
        Function0 function02 = block;
        throw new IllegalArgumentException(("target state must be CREATED or greater, found " + state2).toString());
    }

    public static final <R> Object withCreated(LifecycleOwner $this$withCreated, Function0<? extends R> block, Continuation<? super R> $completion) {
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withCreated.getLifecycle();
        Intrinsics.checkNotNullExpressionValue($this$withStateAtLeastUnchecked$iv, "lifecycle");
        Lifecycle.State state$iv = Lifecycle.State.CREATED;
        MainCoroutineDispatcher lifecycleDispatcher$iv = Dispatchers.getMain().getImmediate();
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded($completion.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
    }

    private static final Object withCreated$$forInline(LifecycleOwner $this$withCreated, Function0 block, Continuation continuation) {
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withCreated.getLifecycle();
        Intrinsics.checkNotNullExpressionValue($this$withStateAtLeastUnchecked$iv, "lifecycle");
        Lifecycle.State state$iv = Lifecycle.State.CREATED;
        MainCoroutineDispatcher lifecycleDispatcher$iv = Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        Continuation continuation2 = null;
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded(continuation2.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        InlineMarker.mark(0);
        Object suspendWithStateAtLeastUnchecked = suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), continuation);
        InlineMarker.mark(1);
        return suspendWithStateAtLeastUnchecked;
    }

    public static final <R> Object withStarted(LifecycleOwner $this$withStarted, Function0<? extends R> block, Continuation<? super R> $completion) {
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withStarted.getLifecycle();
        Intrinsics.checkNotNullExpressionValue($this$withStateAtLeastUnchecked$iv, "lifecycle");
        Lifecycle.State state$iv = Lifecycle.State.STARTED;
        MainCoroutineDispatcher lifecycleDispatcher$iv = Dispatchers.getMain().getImmediate();
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded($completion.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
    }

    private static final Object withStarted$$forInline(LifecycleOwner $this$withStarted, Function0 block, Continuation continuation) {
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withStarted.getLifecycle();
        Intrinsics.checkNotNullExpressionValue($this$withStateAtLeastUnchecked$iv, "lifecycle");
        Lifecycle.State state$iv = Lifecycle.State.STARTED;
        MainCoroutineDispatcher lifecycleDispatcher$iv = Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        Continuation continuation2 = null;
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded(continuation2.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        InlineMarker.mark(0);
        Object suspendWithStateAtLeastUnchecked = suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), continuation);
        InlineMarker.mark(1);
        return suspendWithStateAtLeastUnchecked;
    }

    public static final <R> Object withResumed(LifecycleOwner $this$withResumed, Function0<? extends R> block, Continuation<? super R> $completion) {
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withResumed.getLifecycle();
        Intrinsics.checkNotNullExpressionValue($this$withStateAtLeastUnchecked$iv, "lifecycle");
        Lifecycle.State state$iv = Lifecycle.State.RESUMED;
        MainCoroutineDispatcher lifecycleDispatcher$iv = Dispatchers.getMain().getImmediate();
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded($completion.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
    }

    private static final Object withResumed$$forInline(LifecycleOwner $this$withResumed, Function0 block, Continuation continuation) {
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withResumed.getLifecycle();
        Intrinsics.checkNotNullExpressionValue($this$withStateAtLeastUnchecked$iv, "lifecycle");
        Lifecycle.State state$iv = Lifecycle.State.RESUMED;
        MainCoroutineDispatcher lifecycleDispatcher$iv = Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        Continuation continuation2 = null;
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded(continuation2.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        InlineMarker.mark(0);
        Object suspendWithStateAtLeastUnchecked = suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), continuation);
        InlineMarker.mark(1);
        return suspendWithStateAtLeastUnchecked;
    }

    public static final <R> Object withStateAtLeastUnchecked(Lifecycle $this$withStateAtLeastUnchecked, Lifecycle.State state, Function0<? extends R> block, Continuation<? super R> $completion) {
        MainCoroutineDispatcher lifecycleDispatcher = Dispatchers.getMain().getImmediate();
        boolean dispatchNeeded = lifecycleDispatcher.isDispatchNeeded($completion.getContext());
        if (!dispatchNeeded) {
            if ($this$withStateAtLeastUnchecked.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked.getCurrentState().compareTo(state) >= 0) {
                return block.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked, state, dispatchNeeded, lifecycleDispatcher, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
    }

    private static final Object withStateAtLeastUnchecked$$forInline(Lifecycle $this$withStateAtLeastUnchecked, Lifecycle.State state, Function0 block, Continuation continuation) {
        MainCoroutineDispatcher lifecycleDispatcher = Dispatchers.getMain().getImmediate();
        InlineMarker.mark(3);
        Continuation continuation2 = null;
        boolean dispatchNeeded = lifecycleDispatcher.isDispatchNeeded(continuation2.getContext());
        if (!dispatchNeeded) {
            if ($this$withStateAtLeastUnchecked.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked.getCurrentState().compareTo(state) >= 0) {
                return block.invoke();
            }
        }
        InlineMarker.mark(0);
        Object suspendWithStateAtLeastUnchecked = suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked, state, dispatchNeeded, lifecycleDispatcher, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), continuation);
        InlineMarker.mark(1);
        return suspendWithStateAtLeastUnchecked;
    }

    public static final <R> Object suspendWithStateAtLeastUnchecked(Lifecycle $this$suspendWithStateAtLeastUnchecked, Lifecycle.State state, boolean dispatchNeeded, CoroutineDispatcher lifecycleDispatcher, Function0<? extends R> block, Continuation<? super R> $completion) {
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation co = cancellable$iv;
        C0482x3029b39e withLifecycleStateKt$suspendWithStateAtLeastUnchecked$$inlined$suspendCancellableCoroutine$lambda$1 = new C0482x3029b39e(co, $this$suspendWithStateAtLeastUnchecked, state, block, dispatchNeeded, lifecycleDispatcher);
        if (dispatchNeeded) {
            CoroutineDispatcher coroutineDispatcher = lifecycleDispatcher;
            coroutineDispatcher.dispatch(EmptyCoroutineContext.INSTANCE, new C0483x3029b39f(withLifecycleStateKt$suspendWithStateAtLeastUnchecked$$inlined$suspendCancellableCoroutine$lambda$1, $this$suspendWithStateAtLeastUnchecked, state, block, dispatchNeeded, coroutineDispatcher));
            Lifecycle lifecycle = $this$suspendWithStateAtLeastUnchecked;
        } else {
            CoroutineDispatcher coroutineDispatcher2 = lifecycleDispatcher;
            $this$suspendWithStateAtLeastUnchecked.addObserver(withLifecycleStateKt$suspendWithStateAtLeastUnchecked$$inlined$suspendCancellableCoroutine$lambda$1);
        }
        co.invokeOnCancellation(new C0484x3029b3a0(withLifecycleStateKt$suspendWithStateAtLeastUnchecked$$inlined$suspendCancellableCoroutine$lambda$1, $this$suspendWithStateAtLeastUnchecked, state, block, dispatchNeeded, lifecycleDispatcher));
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result;
    }
}
