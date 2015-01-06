package com.metawiring.descriptors.bundled;

import com.metawiring.descriptors.DefBuilder;
import com.metawiring.types.EntitySampler;
import com.metawiring.types.EntitySamplerService;
import com.metawiring.types.SamplerDef;
import com.metawiring.wiring.GeneratorContext;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * An assortment of retail entities and same-named samplers.
 */
public class RetailEntitySamplerService implements EntitySamplerService {


    // Most of the service methods are delegated to the context
    private GeneratorContext context = new GeneratorContext();

//    // This is enforced as an ugly singleton, for now
//    private static RetailSampleStreamService instance;
//
//    public static SampleStreamService get() {
//        if (instance == null) {
//            synchronized (RetailSampleStreamService.class) {
//                if (instance == null) {
//                    instance = new RetailSampleStreamService();
//                }
//            }
//        }
//        return instance;
//    }

    public RetailEntitySamplerService() {
        DefBuilder defBuilder = new DefBuilder();
        defBuilder.entity("retail.brand").population(1000)
                .field("brand").type("text").function("NamedNumberString;suffix: street");

        defBuilder.sampleEntity("retail.brand").distribution("dist:binomial");

        defBuilder.sampleEntity("retail.brand").distribution("dist:uniform").as("retail.brand binomial");

//        // not graceful syntax yet, but the idea...
//        defBuilder.sampleEntity("retail.brand manifest").distribution("manifest");

        defBuilder.entity("retail.product").population(10000)
                .field("product").type("text").function("file:products")
                .field("product_variant").type("text").function("file:product_variants");
        defBuilder.sampleEntity("retail.product").distribution("dist:uniform").as("retail.product");
//
//        defBuilder.entity("address").population(10000)
//                .field("street_number").type("int").function("hash,")
//                .field("street_name").type("text").function("db:auto")
//                .field("street_unit").type("text").function("db:auto")
//                .field("city").type("text").function("db:cities")
//                .field("state").type("text").function("db:states")
//                .field("country").type("text").function("db:countries")
//                .field("zip_code").type("int").function("db:zipcodes")
//                .field("phone_number").type("int").function("range:100000000-999999999");
//        defBuilder.sampleEntity("address").distribution("uniform").as("address");
//
        context.loadDefs(defBuilder);
    }

    @Override
    public Map<String, EntitySampler> getSampleStreamMap() {
        return Collections.unmodifiableMap(context.getEntitySamplerMap());
    }

    @Override
    public EntitySampler getSampleStream(String name) {
        return context.getEntitySampleStream(name);
    }

    @Override
    public List<SamplerDef> getDefinedEntitySamplers() {
        return context.getDefinedEntitySamplers();
    }

    /**
     CREATE TABLE IF NOT EXISTS retail.employees (
     employee_id int,
     input_time timestamp,

     first_name text,
     last_name text,
     middle_initial text,
     drivers_license text,

     home_address address,
     emergency_contact address,

     country_id int,
     state_id int,
     region_id int,
     store_id int,

     PRIMARY KEY (
     employee_id,
     input_time
     )
     );


     CREATE TABLE IF NOT EXISTS retail.products (
     product_id uuid,

     brand text,
     product text,
     product_variant text,
     cost decimal,
     chain_price decimal,
     msrp decimal,

     PRIMARY KEY (
     product_id
     )
     );


     CREATE TABLE IF NOT EXISTS retail.shelves (
     store_id int,
     product_id uuid,

     brand text,
     product text,
     product_variant text,
     cost decimal,
     chain_price decimal,
     msrp decimal,
     price decimal,

     unit_space int,
     floor_stock int,
     back_stock int,
     ordered_stock int,

     PRIMARY KEY (
     store_id,
     product_id
     )
     );

     /**
     *      CREATE TABLE IF NOT EXISTS retail.stores (
     country_id int,
     state_id int,
     region_id int,
     store_id int,

     tax_rate decimal,

     store_address address,
     manager_id int,

     PRIMARY KEY (
     country_id,
     state_id,
     region_id,
     store_id
     )
     );

     */



    /**

     CREATE TABLE IF NOT EXISTS retail.brand (
     brand text,
     PRIMARY KEY brand
     );

     CREATE TABLE IF NOT EXISTS retail.product (
     product text,
     product_variant text,
     PRIMARY KEY (product, product_variant)
     );


     CREATE TYPE payment (
     amount decimal,

     cash boolean,

     card_type text,
     name text,
     card_hash text,
     expiration_hash text,
     zip_code int
     );

     CREATE TYPE address (
     street_number int,
     street_name text,
     street_unit text,

     city text,
     state text,
     country text,
     zip_code int,
     phone_number int
     );


     CREATE TABLE IF NOT EXISTS retail.stores (
     country_id int,
     state_id int,
     region_id int,
     store_id int,

     tax_rate decimal,

     store_address address,
     manager_id int,

     PRIMARY KEY (
     country_id,
     state_id,
     region_id,
     store_id
     )
     );


     CREATE TABLE IF NOT EXISTS retail.employees (
     employee_id int,
     input_time timestamp,

     first_name text,
     last_name text,
     middle_initial text,
     drivers_license text,

     home_address address,
     emergency_contact address,

     country_id int,
     state_id int,
     region_id int,
     store_id int,

     PRIMARY KEY (
     employee_id,
     input_time
     )
     );


     CREATE TABLE IF NOT EXISTS retail.products (
     product_id uuid,

     brand text,
     product text,
     product_variant text,
     cost decimal,
     chain_price decimal,
     msrp decimal,

     PRIMARY KEY (
     product_id
     )
     );


     CREATE TABLE IF NOT EXISTS retail.shelves (
     store_id int,
     product_id uuid,

     brand text,
     product text,
     product_variant text,
     cost decimal,
     chain_price decimal,
     msrp decimal,
     price decimal,

     unit_space int,
     floor_stock int,
     back_stock int,
     ordered_stock int,

     PRIMARY KEY (
     store_id,
     product_id
     )
     );


     CREATE TABLE IF NOT EXISTS retail.quarterly (
     store_id int,
     fiscal_year int,
     quarter int,
     product_id uuid,
     scan_time timestamp,

     unit_space int,
     floor_stock int,
     back_stock int,
     ordered_stock int,
     damaged int,
     shrinkage int,

     PRIMARY KEY (
     (store_id, fiscal_year, quarter),
     product_id,
     scan_time
     )
     );



     CREATE TABLE IF NOT EXISTS retail.register (
     store_id int,
     register_id int,
     receipt_id timeuuid,
     scan_time timestamp,

     quantity decimal,

     product_id uuid,
     brand text,
     product text,
     product_variant text,
     price decimal,
     msrp decimal,
     savings decimal,

     PRIMARY KEY (
     store_id,
     register_id,
     receipt_id,
     scan_time
     )
     );


     CREATE TABLE IF NOT EXISTS retail.receipt_close (
     receipt_id timeuuid,

     country_id int,
     state_id int,
     region_id int,
     store_id int,
     employee_id int,

     close_time timestamp,
     subtotal decimal,
     tax_rate decimal,
     tax decimal,
     total decimal,

     rewards_id int,

     payments set<payment>,

     PRIMARY KEY (
     receipt_id
     )
     );



     CREATE TABLE IF NOT EXISTS retail.personal_history (
     rewards_id int,
     receipt_id timeuuid,

     PRIMARY KEY (
     receipt_id,
     receipt_id
     )
     );


     CREATE TABLE IF NOT EXISTS retail.rewards_program (
     rewards_id int,

     first_name text,
     last_name text,
     middle_initial text,

     home_address address,

     PRIMARY KEY (
     rewards_id
     )
     );


     */

}
