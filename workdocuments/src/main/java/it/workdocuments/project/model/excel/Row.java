package it.workdocuments.project.model.excel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Row {
    private int rowNumber;
    private List<Cell> cells;

    public Row(RowBuilder builder){
        this.rowNumber = builder.rowNumber;
        this.cells = builder.cells;
    }

    public static class RowBuilder{
        private int rowNumber;
        private List<Cell> cells;

        public RowBuilder rowNumber(int rowNumber){
            this.rowNumber = rowNumber;
            return this;
        }
        public RowBuilder cells(List<Cell> cells){
            this.cells = cells;
            return this;
        }

        public Row build(){
            return new Row(this);
        }
    }
}
