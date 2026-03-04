package com.example;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

import dev.sixpack.api.data.Configuration;
import dev.sixpack.api.data.Context;
import dev.sixpack.api.data.Item;
import dev.sixpack.generator.DynamicSupplier;
import dev.sixpack.generator.annotation.SupplierMetadata;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SupplierMetadata(
        name = MySupplier.NAME,
        description = "Sample dynamic supplier showing runtime manifest construction and refresh.",
        maintainer = "Sixpack Samples Team",
        reportIssueUrl = "https://www.sixpack.dev/supplier",
        reportIssueEmail = "support@sixpack.dev",
        alertEmails = {"support@sixpack.dev"})
public class MySupplier extends DynamicSupplier {

    public static final String NAME = "Front Office Systems";
    private static final long ENHANCED_MANIFEST_DELAY_SECONDS = 20;

    private enum ManifestVersion {
        VERSION1,
        VERSION2
    }

    private volatile ManifestVersion manifestVersion = ManifestVersion.VERSION1;

    public static void main(String... args) {
        var supplier = new MySupplier();
        supplier
                .withClientCertificatePath("config/generator.pem")
                .withClientKeyPath("config/generator.key")
                .bootstrap();
        supplier.scheduleEnhancedManifestRefresh();
    }

    private void scheduleEnhancedManifestRefresh() {
        var scheduler =
                newSingleThreadScheduledExecutor(
                        runnable -> {
                            Thread thread = new Thread(runnable, "MySupplier-ManifestRefreshScheduler");
                            thread.setDaemon(true);
                            return thread;
                        });
        scheduler.schedule(this::triggerEnhancedManifestRefresh,
                ENHANCED_MANIFEST_DELAY_SECONDS, TimeUnit.SECONDS);
        scheduler.shutdown();
    }

    public void triggerEnhancedManifestRefresh() {
        if (ManifestVersion.VERSION2.equals(manifestVersion)) {
            LOGGER.info("Enhanced manifest is already active; skipping refresh trigger.");
            return;
        }
        manifestVersion = ManifestVersion.VERSION2;
        refreshRegistration();
    }

    @Override
    protected List<Item> discoverItems() {
        if (ManifestVersion.VERSION2.equals(manifestVersion)) {
            return List.of(ItemPos.build(true), ItemAtm.build());
        }
        return List.of(ItemPos.build(false));
    }

    @Override
    protected Configuration generate(String itemName, Configuration input, Context context) {
        LOGGER.info("Generating dataset item='{}' with input fields={}.",
                itemName,
                input != null ? input.keySet() : List.of());
        Configuration output;
        if (ItemPos.NAME.equals(itemName)) {
            output = ItemPos.generateOutput(input);
        } else if (ItemAtm.NAME.equals(itemName)) {
            output = ItemAtm.generateOutput(input);
        } else {
            throw new IllegalArgumentException("Unsupported dynamic item '" + itemName + "'.");
        }
        LOGGER.info("Generated dataset item='{}' with output fields={}.",
                itemName, output.keySet());
        return output;
    }
}
