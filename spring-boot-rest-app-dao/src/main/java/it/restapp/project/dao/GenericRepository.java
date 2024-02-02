package it.restapp.project.dao;


import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

public interface GenericRepository<I extends Serializable, E extends Serializable> {
    @NotNull
    List<I> getAll();
    I insert(@NotNull I entity);
    I update(@NotNull I entity);
    void delete(@NotNull I entity);
    void deleteById(@NotNull E id);
    I getById(@NotNull E id);
}
