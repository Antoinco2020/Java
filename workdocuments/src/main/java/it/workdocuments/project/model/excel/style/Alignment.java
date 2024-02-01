package it.workdocuments.project.model.excel.style;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

@Getter
@Setter
public class Alignment {
    private HorizontalAlignment horizontalAlignment;
    private VerticalAlignment verticalAlignment;

    public Alignment(AlignmentBuilder builder){
        this.horizontalAlignment = builder.horizontalAlignment;
        this.verticalAlignment = builder.verticalAlignment;
    }

    public static class AlignmentBuilder{
        private HorizontalAlignment horizontalAlignment;
        private VerticalAlignment verticalAlignment;

        public AlignmentBuilder horizontalAlignment(HorizontalAlignment horizontalAlignment){
            this.horizontalAlignment = horizontalAlignment;
            return this;
        }
        public AlignmentBuilder verticalAlignment(VerticalAlignment verticalAlignment){
            this.verticalAlignment = verticalAlignment;
            return this;
        }

        public Alignment build(){
            return new Alignment(this);
        }
    }
}
