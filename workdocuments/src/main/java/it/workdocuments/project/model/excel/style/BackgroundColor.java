package it.workdocuments.project.model.excel.style;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;

@Getter
@Setter
public class BackgroundColor {
    private FillPatternType fillPatternType;
    private HSSFColor.HSSFColorPredefined fillForegroundColor;
    private HSSFColor.HSSFColorPredefined fillBackgroundColor;

    public BackgroundColor(BackgroundColorBuilder builder){
        this.fillPatternType = builder.fillPatternType;
        this.fillForegroundColor = builder.fillForegroundColor;
        this.fillBackgroundColor = builder.fillBackgroundColor;
    }


    public static class BackgroundColorBuilder{
        private FillPatternType fillPatternType;
        private HSSFColor.HSSFColorPredefined fillForegroundColor;
        private HSSFColor.HSSFColorPredefined fillBackgroundColor;

        public BackgroundColorBuilder fillPatternType(FillPatternType fillPatternType){
            this.fillPatternType = fillPatternType;
            return this;
        }
        public BackgroundColorBuilder fillForegroundColor(HSSFColor.HSSFColorPredefined fillForegroundColor){
            this.fillForegroundColor = fillForegroundColor;
            return this;
        }
        public BackgroundColorBuilder fillBackgroundColor(HSSFColor.HSSFColorPredefined fillBackgroundColor){
            this.fillBackgroundColor = fillBackgroundColor;
            return this;
        }
        public BackgroundColor build(){
            BackgroundColor backgroundColor = new BackgroundColor(this);
            verifyBackgroundColor(backgroundColor);
            return backgroundColor;
        }
        private void verifyBackgroundColor(BackgroundColor backgroundColor){
            if(backgroundColor.getFillBackgroundColor() == null || backgroundColor.getFillForegroundColor() == null
                    || backgroundColor.getFillPatternType() == null){
                throw new IllegalArgumentException("The background colour was not initialised correctly");
            }
        }
    }
}
