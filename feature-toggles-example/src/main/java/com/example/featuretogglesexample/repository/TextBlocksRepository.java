package com.example.featuretogglesexample.repository;

import com.example.featuretogglesexample.model.TextBlock;
import java.util.List;

public interface TextBlocksRepository {
    List<TextBlock> getTextBlock(boolean includePremium);
}
