package com.example;

import dev.sixpack.api.data.Configuration;
import dev.sixpack.api.data.Field;
import dev.sixpack.api.data.FieldType;
import dev.sixpack.api.data.Item;
import dev.sixpack.api.data.ItemType;
import dev.sixpack.api.data.Schema;
import dev.sixpack.api.data.Visibility;
import java.util.List;

final class ItemAtm {

  static final String NAME = "ATM";
  static final String ID = "id";
  static final String ADDRESS_LINE1 = "addressLine1";
  static final String SUPPORTED_CURRENCY = "supportedCurrency";

  private ItemAtm() {}

  static Item build() {
    return Item.builder()
        .name(NAME)
        .description("Adds a row to an atms table with address and supported currencies.")
        .maintainer("Sixpack Samples Team")
        .reportIssueUrl("https://www.sixpack.dev/supplier")
        .visibility(Visibility.VISIBLE)
        .itemType(ItemType.GENERATOR)
        .templates(List.of())
        .input(
            Schema.builder()
                .name("AtmInsertRequest")
                .fields(
                    List.of(
                        Field.inputBuilder()
                            .name(ADDRESS_LINE1)
                            .type(FieldType.String)
                            .description("Primary ATM address line.")
                            .required(true)
                            .build(),
                        Field.inputBuilder()
                            .name(SUPPORTED_CURRENCY)
                            .type(FieldType.Select)
                            .values(List.of("CZK", "EUR"))
                            .description("Primary supported currency.")
                            .required(true)
                            .build()))
                .build())
        .output(
            Schema.builder()
                .name("AtmInsertResult")
                .fields(
                    List.of(
                        Field.outputBuilder().name(ID).type(FieldType.String).build(),
                        Field.outputBuilder().name(ADDRESS_LINE1).type(FieldType.String).build(),
                        Field.outputBuilder().name(SUPPORTED_CURRENCY).type(FieldType.String).build()))
                .build())
        .build();
  }

  static Configuration generateOutput(Configuration input) {
    Configuration output = new Configuration();
    output.put(ID, "atm-" + (System.currentTimeMillis() % 100000));
    copyString(input, output, ADDRESS_LINE1);
    copyString(input, output, SUPPORTED_CURRENCY);
    return output;
  }

  private static void copyString(Configuration input, Configuration output, String fieldName) {
    if (input.containsKey(fieldName)) {
      Object value = input.get(fieldName);
      output.put(fieldName, value != null ? String.valueOf(value) : null);
    }
  }
}
