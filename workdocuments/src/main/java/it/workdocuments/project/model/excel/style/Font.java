package it.workdocuments.project.model.excel.style;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FontUnderline;

@Getter
@Setter
public class Font {
    private String fontName;
    private boolean bold;
    private boolean italicFont;
    private boolean strikeoutText;
    private short fontHeight;
    private short typeOffset;
    private HSSFColor.HSSFColorPredefined color;
    private FontUnderline underline;

    public Font(FontBuilder builder){
        this.fontName = builder.fontName;
        this.bold = builder.bold;
        this.italicFont = builder.italicFont;
        this.strikeoutText = builder.strikeoutText;
        this.fontHeight = builder.fontHeight;
        this.typeOffset = builder.typeOffset;
        this.color = builder.color;
        this.underline = builder.underline;
    }

    public static class FontBuilder{
        private String fontName;
        private boolean bold;
        private boolean italicFont;
        private boolean strikeoutText;
        private short fontHeight;
        private short typeOffset;
        private HSSFColor.HSSFColorPredefined color;
        private FontUnderline underline;

        public FontBuilder fontName(String fontName){
            this.fontName = fontName;
            return this;
        }
        public FontBuilder bold(boolean bold){
            this.bold = bold;
            return this;
        }
        public FontBuilder italicFont(boolean italicFont){
            this.italicFont = italicFont;
            return this;
        }
        public FontBuilder strikeoutText(boolean strikeoutText){
            this.strikeoutText = strikeoutText;
            return this;
        }
        public FontBuilder fontHeight(short fontHeight){
            this.fontHeight = fontHeight;
            return this;
        }
        public FontBuilder typeOffset(short typeOffset){
            this.typeOffset = typeOffset;
            return this;
        }
        public FontBuilder color(HSSFColor.HSSFColorPredefined color){
            this.color = color;
            return this;
        }
        public FontBuilder underline(FontUnderline underline){
            this.underline = underline;
            return this;
        }

        public Font build(){
            return new Font(this);
        }
    }
}
