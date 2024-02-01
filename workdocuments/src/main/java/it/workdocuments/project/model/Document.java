package it.workdocuments.project.model;

import java.util.List;

public interface Document<T> {
    T readDocument(T document) throws Exception;
    String writeDocument(T document) throws Exception;
}
