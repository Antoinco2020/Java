package it.workdocuments.project.model.excel;

import it.workdocuments.project.enums.CellType;
import it.workdocuments.project.model.excel.style.CellStyle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cell {
    private int cellNumber;
    private Object value;
    private String formula;
    private CellStyle headerStyle;
    private CellStyle valueStyle;

    public Cell(CellBuilder builder){
        this.cellNumber = builder.cellNumber;
        this.value = builder.value;
        this.formula = builder.formula;
        this.headerStyle = builder.headerStyle;
        this.valueStyle = builder.valueStyle;
    }

    public static class CellBuilder{
        private int cellNumber;
        private Object value;
        private String formula;
        private CellStyle headerStyle;
        private CellStyle valueStyle;

        public CellBuilder cellNumber(int cellNumber){
            this.cellNumber = cellNumber;
            return this;
        }
        public CellBuilder value(Object value){
            this.value = value;
            return this;
        }
        public CellBuilder formula(String formula){
            this.formula = formula;
            return this;
        }
        public CellBuilder headerStyle(CellStyle headerStyle){
            this.headerStyle = headerStyle;
            return this;
        }
        public CellBuilder valueStyle(CellStyle valueStyle){
            this.valueStyle = valueStyle;
            return this;
        }
        public Cell build() {
            Cell cell =  new Cell(this);
            return cell;
        }
    }
}
