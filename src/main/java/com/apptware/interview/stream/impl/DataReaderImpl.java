package com.apptware.interview.stream.impl;

import com.apptware.interview.stream.DataReader;
import com.apptware.interview.stream.PaginationService;
import jakarta.annotation.Nonnull;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class DataReaderImpl implements DataReader {

  @Autowired private PaginationService paginationService;

  @Override
  public Stream<String> fetchLimitadData(int limit) {
    return fetchPaginatedDataAsStream().limit(limit);
  }

  @Override
  public Stream<String> fetchFullData() {
    return fetchPaginatedDataAsStream();
  }

  /**
   * This method is where the candidate should add the implementation. Logs have been added to track
   * the data fetching behavior. Do not modify any other areas of the code.
   */
  // empty list allData to store fetched data
    List<String> allData = new ArrayList<>();
    int page = 1; // Sets initial page to 1
    int pageSize = 100; // Sets page size to 100
    List<String> paginatedData; // empty list for Collect pagedata

    // Fetching data page by page to till the go paginatedDate is not empty
    do {
      paginatedData = paginationService.getPaginatedData(page, pageSize);
      allData.addAll(paginatedData);
      log.info("Fetched page {} with {} items", page, paginatedData.size());
      page++;
    } while (!paginatedData.isEmpty());

    // return Converts allData list to stream & logs each item using peek()
    return allData.stream().peek(item -> log.info("Fetched Item: {}", item));
  }
}
