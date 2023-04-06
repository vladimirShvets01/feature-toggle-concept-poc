package com.example.featuretogglesexample.repository;

import com.example.featuretogglesexample.model.TextBlock;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class TextBlocksRepositoryMemImpl implements TextBlocksRepository {

    private List<TextBlock> IN_MEMORY_TEXT = List.of(
        new TextBlock("Text Block 1", true),
        new TextBlock("Text Block 2", false),
        new TextBlock("Text Block 3", true)
    );

    @Override
    public List<TextBlock> getTextBlock(boolean includePremium) {
        return includePremium ? IN_MEMORY_TEXT :
            IN_MEMORY_TEXT.stream().filter(j -> !j.premium())
                .collect(Collectors.toList());
    }
}
