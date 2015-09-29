package com.alive_n_clickin.commutity.domain.flaggable;

import com.alive_n_clickin.commutity.domain.flag.IFlag;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract implementation of the IFlaggable interface. This provides default implementations
 * for concrete classes.
 */
public abstract class AbstractFlaggable implements IFlaggable {
    private List<IFlag> flags = new ArrayList<>();

    @Override
    public void addFlag(IFlag flag) {
        if (flag == null) {
            throw new IllegalArgumentException();
        }

        this.flags.add(flag);
    }

    @Override
    public void removeFlag(IFlag flag) {
        if (flag == null) {
            throw new IllegalArgumentException();
        }

        this.flags.remove(flag);
    }

    @Override
    public List<IFlag> getFlags() {
        return new ArrayList<>(flags);
    }
}
