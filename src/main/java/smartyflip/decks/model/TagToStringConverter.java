/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.decks.model;

import org.modelmapper.AbstractConverter;

public class TagToStringConverter extends AbstractConverter<Tag, String> {
    @Override
    protected String convert(Tag source) {
        return source.getTagName();
    }

    public String convertToDatabaseColumn(String tag) {
        return tag;
    }
}