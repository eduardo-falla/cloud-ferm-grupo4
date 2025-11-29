package com.cloudferm.config;

import com.cloudferm.dto.RegisterRequest;
import com.cloudferm.model.*;
import com.cloudferm.repository.BatchRepository;
import com.cloudferm.repository.RecordRepository;
import com.cloudferm.repository.UserRepository;
import com.cloudferm.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final BatchRepository batchRepository;
    private final RecordRepository recordRepository;
    private final com.cloudferm.repository.CocoaVarietyRepository varietyRepository;
    private final com.cloudferm.repository.FarmRepository farmRepository;
    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            System.out.println("=== Starting Data Seeding ===");
            
            // 0. Create Catalogs
            createVarieties();
            createFarms();

            // 1. Create Admin
            createAdmin();
            
            // 2. Create 3 Operators
            User operator1 = createOperator("Carlos Mendoza", "carlos@cloudferm.com", "carlos123");
            User operator2 = createOperator("María García", "maria@cloudferm.com", "maria123");
            User operator3 = createOperator("Juan Pérez", "juan@cloudferm.com", "juan123");
            
            // 3. Create Batches for each operator
            createBatchesForOperator(operator1, "CCN-51", 1);
            createBatchesForOperator(operator2, "CCN-50", 5);
            createBatchesForOperator(operator3, "CCN-49", 9);
            
            System.out.println("=== Data Seeding Complete ===");
        }
    }

    private void createVarieties() {
        createVariety("CCN-51", "High yield variety, resistant to disease.", 6);
        createVariety("CCN-50", "Experimental variety.", 6);
        createVariety("CCN-49", "Experimental variety.", 6);
        System.out.println("✓ Varieties created");
    }

    private void createVariety(String name, String description, int days) {
        // Create a dummy admin user reference for seeding
        User admin = new User();
        admin.setId(1L);

        CocoaVariety variety = CocoaVariety.builder()
                .name(name)
                .description(description)
                .optimalFermentationDays(days)
                .createdAt(LocalDateTime.now())
                .createdBy(admin)
                .build();
        varietyRepository.save(variety);
    }

    private void createFarms() {
        createFarm("Finca Don Valentin", "Los Ríos", "Familia González");
        System.out.println("✓ Farms created");
    }

    private void createFarm(String name, String location, String owner) {
        // Create a dummy admin user reference for seeding
        User admin = new User();
        admin.setId(1L);

        Farm farm = Farm.builder()
                .name(name)
                .location(location)
                .ownerName(owner)
                .createdAt(LocalDateTime.now())
                .createdBy(admin)
                .build();
        farmRepository.save(farm);
    }

    private void createAdmin() {
        RegisterRequest admin = RegisterRequest.builder()
                .fullName("Admin User")
                .email("admin@cloudferm.com")
                .password("admin123")
                .role(Role.ADMIN)
                .build();
        authService.register(admin);
        System.out.println("✓ Admin created: admin@cloudferm.com / admin123");
    }

    private User createOperator(String fullName, String email, String password) {
        RegisterRequest operator = RegisterRequest.builder()
                .fullName(fullName)
                .email(email)
                .password(password)
                .role(Role.OPERATOR)
                .build();
        authService.register(operator);
        System.out.println("✓ Operator created: " + email + " / " + password);
        return userRepository.findByEmail(email).orElseThrow();
    }

    private void createBatchesForOperator(User operator, String varietyName, int startId) {
        CocoaVariety variety = varietyRepository.findByName(varietyName).orElseThrow();
        Farm farm = farmRepository.findAll().get(0); // Assign Finca Don Valentin

        // Create 2 ACTIVE batches
        createBatch(operator, "BATCH-2025-" + String.format("%03d", startId), variety, farm,
                   LocalDate.now().minusDays(8), null, BatchStatus.ACTIVE, 12);
        createBatch(operator, "BATCH-2025-" + String.format("%03d", startId + 1), variety, farm,
                   LocalDate.now().minusDays(3), null, BatchStatus.ACTIVE, 6);
        
        // Create 2 FINISHED batches
        createBatch(operator, "BATCH-2024-" + String.format("%03d", startId + 2), variety, farm,
                   LocalDate.now().minusDays(25), LocalDate.now().minusDays(18), BatchStatus.FINISHED, 14);
        createBatch(operator, "BATCH-2024-" + String.format("%03d", startId + 3), variety, farm,
                   LocalDate.now().minusDays(40), LocalDate.now().minusDays(34), BatchStatus.FINISHED, 12);
    }

    private void createBatch(User operator, String code, CocoaVariety variety, Farm farm, LocalDate startDate, 
                            LocalDate endDate, BatchStatus status, int recordCount) {
        double weight = 400 + random.nextDouble() * 200; // 400-600 kg
        
        Batch batch = new Batch();
        batch.setCode(code);
        batch.setCocoaVariety(variety);
        batch.setFarm(farm);
        batch.setGrossWeight(Math.round(weight * 10.0) / 10.0);
        batch.setStartDate(startDate);
        batch.setEndDate(endDate);
        batch.setStatus(status);
        batch.setOperator(operator);
        batchRepository.save(batch);
        
        // Generate records
        generateRecords(batch, recordCount);
        
        System.out.println("  ✓ Batch " + code + " (" + status + ") with " + recordCount + " records");
    }

    private void generateRecords(Batch batch, int count) {
        LocalDateTime startTime = batch.getStartDate().atStartOfDay().plusHours(8); // Start at 8 AM
        
        for (int i = 0; i < count; i++) {
            DailyRecord record = new DailyRecord();
            record.setBatch(batch);
            record.setRecordedAt(startTime.plusHours(i * 12)); // Every 12 hours
            
            // Simulate realistic fermentation curve
            double dayProgress = (double) i / count;
            
            // Temperature: rises to peak around day 3-4, then decreases
            double tempPeak = 48.0 + random.nextDouble() * 4; // 48-52°C peak
            double tempMass;
            if (dayProgress < 0.4) {
                // Rising phase
                tempMass = 30.0 + (tempPeak - 30.0) * (dayProgress / 0.4);
            } else if (dayProgress < 0.7) {
                // Peak phase
                tempMass = tempPeak - random.nextDouble() * 2;
            } else {
                // Cooling phase
                tempMass = tempPeak - ((dayProgress - 0.7) / 0.3) * (tempPeak - 35.0);
            }
            tempMass += (random.nextDouble() - 0.5) * 2; // Add variation
            
            // pH: decreases from ~5.5 to ~4.2
            double ph = 5.5 - (dayProgress * 1.3) + (random.nextDouble() - 0.5) * 0.3;
            ph = Math.max(4.0, Math.min(5.8, ph));
            
            // Humidity decreases over time
            double humidityMass = 75.0 - (dayProgress * 15) + random.nextDouble() * 5;
            
            record.setTempMass(Math.round(tempMass * 10.0) / 10.0);
            record.setTempAmbient(24.0 + random.nextDouble() * 6);
            record.setHumidityMass(Math.round(humidityMass * 10.0) / 10.0);
            record.setHumidityAmbient(65.0 + random.nextDouble() * 15);
            record.setPhLevel(Math.round(ph * 100.0) / 100.0);
            record.setTurned(i % 4 == 0); // Turn every 48 hours
            
            // Varied observations
            String[] observations = {
                "Fermentation progressing normally. Good aroma.",
                "Temperature stable. Mass turned successfully.",
                "Slight acidic smell developing. Normal for this stage.",
                "Chocolate aroma becoming more pronounced.",
                "Mass consistency good. No signs of over-fermentation.",
                "Monitoring closely. All parameters within range."
            };
            record.setOperatorObservations(observations[random.nextInt(observations.length)]);

            recordRepository.save(record);
        }
    }
}
