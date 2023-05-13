package com.example.document.dto;

import com.example.document.entity.Block;
import com.example.document.entity.Document;
import lombok.Data;

import java.util.*;

@Data
public class JsonDocumentDto {

    private String title;

    private String id;

    private ArrayList<LinkedHashMap> blockList;

    public Document documentEntityOut(){

        Document document = new Document();
        document.setTitle(this.title);
        document.setDocumentId(this.id);

        return document;
    }

    public List<Block> blockEntityOut(){

        List<Block> blockEntityList = new ArrayList<>();

        for (int i = 0; i < blockList.size(); i++) {

            LinkedHashMap blockData = blockList.get(i);

            Block block = new Block();

            UUID uuid = UUID.randomUUID();
            block.setBlockId(uuid.toString());
            block.setDocumentId(this.id);
            block.setIndex(Integer.parseInt(blockData.get("index").toString()));
            block.setContent(blockData.get("content").toString());
            block.setCategory(blockData.get("category").toString());

            blockEntityList.add(block);
        }

        return blockEntityList;
    }
}
