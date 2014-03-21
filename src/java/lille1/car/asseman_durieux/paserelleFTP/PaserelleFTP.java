/*
 * The MIT License
 *
 * Copyright 2014 Thomas Durieux.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package lille1.car.asseman_durieux.paserelleFTP;

import java.io.InputStream;
import javax.ws.rs.core.Response;

/**
 *
 * @author Thomas Durieux
 */
public interface PaserelleFTP {

    /**
     * Download a file
     *
     * @param path the path to the file
     *
     * @return Response
     */
    Response getFile(String path);

    /**
     * Remove a file
     *
     * @param path the path to the file
     *
     * @return Response
     */
    Response removeFile(String path);

    /**
     * Store a file
     *
     * @param path the path to the file
     *
     * @return Response
     */
    Response storeFile(String path, InputStream file);

    /**
     * Get a representation of a Directory
     *
     * @param path The path to the direcory
     * @param format The output format (JSON, XML, HTML)
     *
     * @return
     */
    Response getDir(String path, String format);

    /**
     * Create a directory
     *
     * @param path The path to the direcory
     *
     * @return
     */
    Response mkDir(String path);

    /**
     * Remove a Directory
     *
     * @param path The path to the direcory
     *
     * @return
     */
    Response rmDir(String path);

    /**
     * Rename a resource
     *
     * @param fromPath the old path of the resource
     * @param toPath the new path of the resource
     *
     * @return
     */
    Response rename(String fromPath, String toPath);
}
