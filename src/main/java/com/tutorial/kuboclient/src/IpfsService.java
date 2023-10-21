package com.tutorial.kuboclient.src;

import io.ipfs.api.IPFS;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class IpfsService {
    private final IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
    String createContents(MultipartFile mfile) throws IOException {
        ipfs.refs.local();
//        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File("hello.txt"));
        NamedStreamable.ByteArrayWrapper contents = new NamedStreamable.ByteArrayWrapper(mfile.getOriginalFilename(), mfile.getBytes());
        System.out.println("it works");
        String cid = ipfs.add(contents).get(0).toString();
        System.out.println(cid);
        return cid;
    }

    byte[] getContents(String cid) throws IOException {
        Multihash filePointer = Multihash.fromBase58(cid);
        byte[] fileContents = ipfs.cat(filePointer);

        return fileContents;
    }
}
