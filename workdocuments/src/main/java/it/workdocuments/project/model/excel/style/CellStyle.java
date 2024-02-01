package it.workdocuments.project.model.excel.style;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.BorderStyle;

@Getter
@Setter
public class CellStyle {
    private Alignment alignment;
    private BorderStyle borderTop;
    private BorderStyle borderBottom;
    private BorderStyle borderLeft;
    private BorderStyle borderRight;
    private BackgroundColor backgroundColor;
    private Font font;

    public CellStyle(CellStyleBuilder builder){
        this.alignment = builder.alignment;
        this.borderTop = builder.borderTop;
        this.borderBottom = builder.borderBottom;
        this.borderLeft = builder.borderLeft;
        this.borderRight = builder.borderRight;
        this.backgroundColor = builder.backgroundColor;
        this.font = builder.font;
    }

    public static class CellStyleBuilder{
        private Alignment alignment;
        private BorderStyle borderTop;
        private BorderStyle borderBottom;
        private BorderStyle borderLeft;
        private BorderStyle borderRight;
        private BackgroundColor backgroundColor;
        private Font font;

        public CellStyleBuilder alignment(Alignment alignment){
            this.alignment = alignment;
            return this;
        }
        public CellStyleBuilder borderTop(BorderStyle borderTop){
            this.borderTop = borderTop;
            return this;
        }
        public CellStyleBuilder borderBottom(BorderStyle borderBottom){
            this.borderBottom = borderBottom;
            return this;
        }
        public CellStyleBuilder borderLeft(BorderStyle borderLeft){
            this.borderLeft = borderLeft;
            return this;
        }
        public CellStyleBuilder borderRight(BorderStyle borderRight){
            this.borderRight = borderRight;
            return this;
        }
        public CellStyleBuilder backgroundColor(BackgroundColor backgroundColor){
            this.backgroundColor = backgroundColor;
            return this;
        }
        public CellStyleBuilder font(Font font){
            this.font = font;
            return this;
        }
        public CellStyle build(){
            return new CellStyle(this);
        }
    }
}
