package com.goeuro.command;

import com.goeuro.core.api.API;
import com.goeuro.core.api.APICommand;
import com.goeuro.core.api.APICommandContext;
import com.goeuro.core.InvalidCommandContextException;
import com.goeuro.core.api.Command;
import com.goeuro.dto.LocationDTO;
import com.goeuro.service.csv.CSVService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.goeuro.service.logger.CustomConsoleLogger.error;
import static com.goeuro.service.logger.CustomConsoleLogger.message;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * API command.
 *
 * Fetch locations by city name and store into the CSV file.
 */
@API(
    name = "getLocationsByCityCSV",
    description = "Fetch locations by city and import into CSV file",
    template = "/position/suggest/${local}/{city}",
    allowedParams = {"locale: e.g. en, de, fr", "city: e.g. Berlin, London, Munich"},
    example = "/position/suggest/en/Berlin"
)
public class GetLocationsByCityCSV extends APICommand implements Command {

    private final String CITY_PARAM = "city";

    private final String LOCALE_PARAM = "locale";

    private final int EXPECTED_STATUS = 200;

    private final String DATETIME_PATTERN = "YYYY_MM_dd_HH_mm_ss";

    private final String[] CSV_HEADERS = {"_id", "name", "type", "latitude", "longitude" };

    @Autowired
    private CSVService csvService;

    /**
     * Fetch locations by city and store it to the CSV file.
     *
     * @param context the http request context {@link APICommandContext}
     */
    @Override
    public void execute(APICommandContext context) {

        LocationDTO[] locations = run(context, LocationDTO[].class, EXPECTED_STATUS);

        if (locations.length == 0) {
            csvService.createCSVFile(Collections.emptyList(), CSV_HEADERS, getFileName());
            return;
        }

        Collection<Collection<Object>> locationCSVFormat = Stream.of(locations).map(l ->
                        Arrays.<Object>asList(
                                l.getId(),
                                l.getName(),
                                l.getLocationType(),
                                l.getGeoPosition().getLongitude(),
                l.getGeoPosition().getLatitude())
        ).collect(Collectors.toList());

        csvService.createCSVFile(locationCSVFormat, CSV_HEADERS, getFileName());
    }

    /**
     * Prepare url of http request.
     *
     * @return The url of http request
     */
    @Override
    protected String getUrl() {

        String urlTemplate = String.format("http://%s/%s/position/suggest/${%s}/${%s}",
                getBaseUrl(),
                getApiVersion(),
                LOCALE_PARAM,
                CITY_PARAM);

        String city = getContext().getPathParams().get(CITY_PARAM);

        String locale = StringUtils.isEmpty(getContext().getPathParams().get(LOCALE_PARAM)) ?
                getDefaultLocale() : getContext().getPathParams().get(LOCALE_PARAM);

        return urlTemplate
                .replaceAll("\\$\\{" + CITY_PARAM + "\\}", city)
                .replaceAll("\\$\\{"+ LOCALE_PARAM +"\\}", locale);
    }

    /**
     * Validate of API command context.
     */
    @Override
    protected void validateContext() {
        if (StringUtils.isEmpty(getContext().getPathParams().get(CITY_PARAM))) {
            String message = message("Path parameter %s can't be empty", CITY_PARAM);
            error(message);
            throw new InvalidCommandContextException(message);
        }
    }

    /**
     * Prepares the filename of CSV file.
     *
     * @return The filename of the future CSV file.
     */
    private String getFileName() {
        assertThat(getContext()).isNotNull();

        String date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(DATETIME_PATTERN)).toString();

        return String.format("%s_locations_%s.csv", getContext().getPathParams().get(CITY_PARAM), date);
    }

}
