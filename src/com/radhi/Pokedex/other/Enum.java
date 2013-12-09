package com.radhi.Pokedex.other;

public class Enum {
    public enum NameType {
        ENGLISH("eng_name"),
        JAPANESE("jap_name"),
        ROMAJI("romaji_name"),
        SPECIES("species");

        private String name_type;

        private NameType(String name_type) {
            this.name_type = name_type;
        }

        public String getNameType() {
            return name_type;
        }
    }

    public enum ImgSize {
        SMALL("unknown_small.png"),
        LARGE("unknown_large.png");

        private String img_Size;

        private ImgSize(String img_Size) {
            this.img_Size = img_Size;
        }

        public String getImgSize() {
            return img_Size;
        }
    }

    public enum EfficacyMode {
        OFFENCE, DEFENCE
    }

    public enum Effectiveness {
        SUPER_EFFECTIVE, IMMUNE, NOT_EFFECTIVE
    }
}
