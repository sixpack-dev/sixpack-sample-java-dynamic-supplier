package com.example;

import dev.sixpack.api.data.Configuration;
import dev.sixpack.api.data.Field;
import dev.sixpack.api.data.FieldType;
import dev.sixpack.api.data.Item;
import dev.sixpack.api.data.ItemType;
import dev.sixpack.api.data.Schema;
import dev.sixpack.api.data.Visibility;

import java.util.ArrayList;
import java.util.List;

import static com.example.UtilsInputValue.resolveString;

final class ItemPos {

    static final String NAME = "Point_of_Sales";
    static final String ID = "id";
    static final String ADDRESS_LINE1 = "addressLine1";
    static final String CITY = "city";
    static final String POSTAL_CODE = "postalCode";
    static final String COUNTRY_CODE = "countryCode";
    static final String FOR_LOANS = "forLoans";
    static final String CASH_DESK = "cashDesk";

    private ItemPos() {
    }

    static Item build(boolean includeCashDesk) {
        List<Field> inputFields = new ArrayList<>();
        inputFields.add(
                Field.inputBuilder()
                        .name(ADDRESS_LINE1)
                        .type(FieldType.String)
                        .description("Primary address line.")
                        .nullDescription("If null, a random address line is generated.")
                        .required(false)
                        .build());
        inputFields.add(
                Field.inputBuilder()
                        .name(CITY)
                        .type(FieldType.String)
                        .description("City name.")
                        .nullDescription("If null, a random city is generated.")
                        .required(false)
                        .build());
        inputFields.add(
                Field.inputBuilder()
                        .name(POSTAL_CODE)
                        .type(FieldType.String)
                        .description("Postal code.")
                        .nullDescription("If null, a random postal code is generated.")
                        .required(false)
                        .build());
        inputFields.add(
                Field.inputBuilder()
                        .name(COUNTRY_CODE)
                        .type(FieldType.String)
                        .description("ISO country code, for example CZ or FR.")
                        .nullDescription("If null, a random supported country is selected.")
                        .required(false)
                        .build());
        inputFields.add(
                Field.inputBuilder()
                        .name(FOR_LOANS)
                        .type(FieldType.Boolean)
                        .description("Whether this point of sale should be enabled for loans.")
                        .nullDescription("Default is true.")
                        .required(false)
                        .build());
        if (includeCashDesk) {
            inputFields.add(
                    Field.inputBuilder()
                            .name(CASH_DESK)
                            .type(FieldType.Boolean)
                            .description("Whether this point of sale has a cash desk.")
                            .nullDescription("Default is false.")
                            .required(false)
                            .build());
        }

        return Item.builder()
                .name(NAME)
                .description("Adds a row to a points_of_sales table using a simple address.")
                .maintainer("Sixpack Samples Team")
                .reportIssueUrl("https://www.sixpack.dev/supplier")
                .reportIssueEmail("support@sixpack.dev")
                .alertEmails(new String[]{"support@sixpack.dev"})
                .visibility(Visibility.VISIBLE)
                .itemType(ItemType.GENERATOR)
                .templates(List.of())
                .input(Schema.builder().name("PointOfSaleInsertRequest").fields(inputFields).build())
                .output(
                        Schema.builder()
                                .name("PointOfSaleInsertResult")
                                .fields(buildOutputFields(includeCashDesk))
                                .build())
                .build();
    }

    private static List<Field> buildOutputFields(boolean includeCashDesk) {
        List<Field> outputFields = new ArrayList<>();
        outputFields.add(Field.outputBuilder().name(ID).type(FieldType.String).build());
        outputFields.add(Field.outputBuilder().name(ADDRESS_LINE1).type(FieldType.String).build());
        outputFields.add(Field.outputBuilder().name(CITY).type(FieldType.String).build());
        outputFields.add(Field.outputBuilder().name(POSTAL_CODE).type(FieldType.String).build());
        outputFields.add(Field.outputBuilder().name(COUNTRY_CODE).type(FieldType.String).build());
        outputFields.add(Field.outputBuilder().name(FOR_LOANS).type(FieldType.Boolean).build());
        if (includeCashDesk) {
            outputFields.add(Field.outputBuilder().name(CASH_DESK).type(FieldType.Boolean).build());
        }
        return outputFields;
    }

    static Configuration generateOutput(Configuration input) {
        Configuration output = new Configuration();
        output.put(ID, "pos-" + (System.currentTimeMillis() % 100000));
        output.put(
                ADDRESS_LINE1,
                resolveString(input, ADDRESS_LINE1, UtilsRandomData.randomAddressLine()));
        output.put(CITY, resolveString(input, CITY, UtilsRandomData.randomCity()));
        output.put(
                POSTAL_CODE,
                resolveString(input, POSTAL_CODE, UtilsRandomData.randomPostalCode()));
        output.put(
                COUNTRY_CODE,
                resolveString(input, COUNTRY_CODE, UtilsRandomData.randomCountryCode()));
        output.put(FOR_LOANS, UtilsInputValue.resolveBoolean(input, FOR_LOANS, true));
        output.put(CASH_DESK, UtilsInputValue.resolveBoolean(input, CASH_DESK, false));
        return output;
    }

}
