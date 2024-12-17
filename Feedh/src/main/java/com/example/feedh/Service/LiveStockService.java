package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.LiveStockDTOout;
import com.example.feedh.Model.Farm;
import com.example.feedh.Model.LiveStock;
import com.example.feedh.Repostiory.FarmRepository;
import com.example.feedh.Repostiory.LiveStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LiveStockService {
    private final LiveStockRepository liveStockRepository;
    private final FarmRepository farmRepository;

    public List<LiveStockDTOout> getAllLiveStocks() {
        List<LiveStock> liveStocks = liveStockRepository.findAll();
        List<LiveStockDTOout> liveStockDTOS = new ArrayList<>();

        for (LiveStock ls : liveStocks) {
            liveStockDTOS.add(new LiveStockDTOout(ls.getType(), ls.getBreed(), ls.getQuantity(), ls.getFeedType()));
        }
        return liveStockDTOS;
    }

    public void addLiveStock(Integer farmId, LiveStock liveStock) {
        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null) {
            throw new ApiException("Farm with ID: " + farmId + " was not found");
        }
        liveStock.setFarm(farm);
        liveStockRepository.save(liveStock);
    }

    public void updateLiveStock(Integer liveStockId, LiveStock liveStock) {
        LiveStock oldLiveStock = liveStockRepository.findLiveStockById(liveStockId);
        if (oldLiveStock == null) {
            throw new ApiException("Live Stock with ID: " + liveStockId + " was not found");
        }
        oldLiveStock.setType(liveStock.getType());
        oldLiveStock.setBreed(liveStock.getBreed());
        oldLiveStock.setQuantity(liveStock.getQuantity());
        oldLiveStock.setFeedType(liveStock.getFeedType());
        liveStockRepository.save(oldLiveStock);
    }

    public void deleteLiveStock(Integer liveStockId) {
        LiveStock liveStock = liveStockRepository.findLiveStockById(liveStockId);
        if (liveStock == null) {
            throw new ApiException("Live Stock with ID: " + liveStockId + " was not found");
        }
        liveStockRepository.delete(liveStock);
    }
}
